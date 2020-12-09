package com.atmaneuler.hsdps.data.remote

import androidx.preference.PreferenceManager
import com.atmaneuler.hsdps.data.local.LUser
import com.example.firebaseStarterKit.MainApplication
import com.facebook.stetho.inspector.network.DefaultResponseHandler
import com.facebook.stetho.inspector.network.NetworkEventReporter.InspectorRequest
import com.facebook.stetho.inspector.network.NetworkEventReporter.InspectorResponse
import com.facebook.stetho.inspector.network.NetworkEventReporterImpl
import com.facebook.stetho.inspector.network.RequestBodyHelper
import com.google.gson.Gson
import okhttp3.*
import okio.BufferedSource
import okio.buffer
import okio.sink
import okio.source
import java.io.IOException
import java.io.InputStream


/**
 * Provides easy integration with [OkHttp](http://square.github.io/okhttp/) 3.x by way of
 * the new [Interceptor](https://github.com/square/okhttp/wiki/Interceptors) system. To
 * use:
 * <pre>
 * OkHttpClient client = new OkHttpClient.Builder()
 * .addNetworkInterceptor(new StethoInterceptor())
 * .build();
</pre> *
 */
class FaceInterceptor : Interceptor {
    private val mEventReporter = NetworkEventReporterImpl.get()

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestId = mEventReporter.nextRequestId()
        var request = chain.request()

        val pref = PreferenceManager.getDefaultSharedPreferences(MainApplication.appContext)
        if (request.header("No-Authentication") == null) {
            val value = pref.getString(com.atmaneuler.hsdps.base.Constant.KEY_USER_CREDENTIALS, "")
            if (!value.isNullOrEmpty()) {
                val data = Gson().fromJson(value, LUser::class.java)
                if (!data.token.isNullOrBlank()) {
                    val finalToken = "Bearer ${data.token}"
                    request = request.newBuilder()
                        .addHeader("Authorization", finalToken)
                        .addHeader("Accept", "application/json")
                        .build()
                }
            }
        }

        var requestBodyHelper: RequestBodyHelper? = null
        if (mEventReporter.isEnabled) {
            requestBodyHelper = RequestBodyHelper(mEventReporter, requestId)
            val inspectorRequest =
                OkHttpInspectorRequest(requestId, request, requestBodyHelper)
            mEventReporter.requestWillBeSent(inspectorRequest)
        }
        var response: Response

        response = try {
            chain.proceed(request)
        } catch (e: IOException) {
            if (mEventReporter.isEnabled) {
                mEventReporter.httpExchangeFailed(requestId, e.toString())
            }
            throw e
        }

        if (response.code == 401) {
            return response.newBuilder().body(response.body)
                .code(401)
                .build()
        }

        if (true) {
            if (requestBodyHelper != null && requestBodyHelper.hasBody()) {
                requestBodyHelper.reportDataSent()
            }
            val connection = chain.connection()
                ?: throw IllegalStateException(
                    "No connection associated with this request; " +
                            "did you use addInterceptor instead of addNetworkInterceptor?"
                )

            mEventReporter.responseHeadersReceived(
                OkHttpInspectorResponse(
                    requestId,
                    request,
                    response,
                    connection
                )
            )

            val body = response.body
            var contentType: MediaType? = null
            var responseStream: InputStream? = null
            if (body != null) {
                contentType = body.contentType()
                responseStream = body.byteStream()
            }

            responseStream = mEventReporter.interpretResponseStream(
                requestId,
                contentType?.toString(),
                response.header("Content-Encoding"),
                responseStream,
                DefaultResponseHandler(mEventReporter, requestId)
            )
            if (responseStream != null) {
                /*
                response = response.newBuilder()
                    .body(ForwardingResponseBody(body, responseStream))
                    .build()

                 */
                response = response.newBuilder().body(ForwardingResponseBody(body, responseStream))
                    .code(200)
                    .build()
            } else {
                response = response.newBuilder().body(response.body)
                    .code(200)
                    .build()
            }
        }
        return response
    }

    private class OkHttpInspectorRequest(
        private val mRequestId: String,
        private val mRequest: Request,
        private val mRequestBodyHelper: RequestBodyHelper
    ) : InspectorRequest {
        override fun id(): String {
            return mRequestId
        }

        override fun friendlyName(): String? {
            // Hmm, can we do better?  tag() perhaps?
            return null
        }

        override fun friendlyNameExtra(): Int? {
            return null
        }

        override fun url(): String {
            return mRequest.url.toString()
        }

        override fun method(): String {
            return mRequest.method
        }

        @Throws(IOException::class)
        override fun body(): ByteArray? {
            val body = mRequest.body ?: return null
            val out =
                mRequestBodyHelper.createBodySink(firstHeaderValue("Content-Encoding"))
            val bufferedSink = out.sink().buffer()
            try {
                body.writeTo(bufferedSink)
            } finally {
                bufferedSink.close()
            }
            return mRequestBodyHelper.displayBody
        }

        override fun headerCount(): Int {
            return mRequest.headers.size
        }

        override fun headerName(index: Int): String {
            return mRequest.headers.name(index)
        }

        override fun headerValue(index: Int): String {
            return mRequest.headers.value(index)
        }

        override fun firstHeaderValue(name: String): String? {
            return mRequest.header(name)
        }

    }

    private class OkHttpInspectorResponse(
        private val mRequestId: String,
        private val mRequest: Request,
        private val mResponse: Response,
        private val mConnection: Connection?
    ) : InspectorResponse {
        override fun requestId(): String {
            return mRequestId
        }

        override fun url(): String {
            return mRequest.url.toString()
        }

        override fun statusCode(): Int {
            return mResponse.code
        }

        override fun reasonPhrase(): String {
            return mResponse.message
        }

        override fun connectionReused(): Boolean {
            // Not sure...
            return false
        }

        override fun connectionId(): Int {
            return mConnection?.hashCode() ?: 0
        }

        override fun fromDiskCache(): Boolean {
            return mResponse.cacheResponse != null
        }

        override fun headerCount(): Int {
            return mResponse.headers.size
        }

        override fun headerName(index: Int): String {
            return mResponse.headers.name(index)
        }

        override fun headerValue(index: Int): String {
            return mResponse.headers.value(index)
        }

        override fun firstHeaderValue(name: String): String? {
            return mResponse.header(name)
        }

    }

    private class ForwardingResponseBody(
        private val mBody: ResponseBody?,
        interceptedStream: InputStream
    ) :
        ResponseBody() {
        private val mInterceptedSource: BufferedSource
        override fun contentType(): MediaType? {
            return mBody!!.contentType()
        }

        override fun contentLength(): Long {
            return mBody!!.contentLength()
        }

        override fun source(): BufferedSource {
            // close on the delegating body will actually close this intercepted source, but it
            // was derived from mBody.byteStream() therefore the close will be forwarded all the
            // way to the original.
            return mInterceptedSource
        }

        init {
            mInterceptedSource = interceptedStream.source().buffer()
        }
    }
}
