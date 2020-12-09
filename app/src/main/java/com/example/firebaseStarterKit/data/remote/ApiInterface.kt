package com.atmaneuler.hsdps.data.remote

import com.atmaneuler.hsdps.base.Constant
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface ApiInterface {
   /* @Headers(
        "Accept: application/json",
        "Content-type:application/json-patch+json"
    )
    @POST("Auth/Login")
    fun loginUsernamPassword(
        @Body user: JsonObject
    ): Observable<ResponseBase<ResponseUser>>

    @Headers(
        "Accept: application/json",
        "Content-type:application/json-patch+json"
    )
    @POST("Auth/LoginByDevice")
    fun loginMacAddress(
        @Body user: JsonObject
    ): Observable<ResponseBase<ResponseUser>>*/

    companion object {

        private lateinit var retrofit: Retrofit
        private var client = OkHttpClient.Builder().apply {
            connectTimeout(45, TimeUnit.SECONDS)
            writeTimeout(45, TimeUnit.SECONDS)
            readTimeout(45, TimeUnit.SECONDS)
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
                .retryOnConnectionFailure(true)//remove ??
            //addInterceptor(SupportInterceptor())
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//                val tlsSocketFactory = TLSSocketFactory()
//                sslSocketFactory(tlsSocketFactory, tlsSocketFactory.trustManager)
//            }
        }.build()

        private var gsonConverter = GsonBuilder()
            .setLenient()
            .create()

        private var converter = GsonConverterFactory.create(gsonConverter)

        private fun baseUrl(): String {
            return "${Constant.BASEURL}/api/"
        }

        fun getClient(): Retrofit {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl())
                .client(client)
                .addConverterFactory(converter)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
            return retrofit
        }


        private var clientWithToken = OkHttpClient.Builder().apply {
            connectTimeout(20, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            authenticator(TokenAuthenticator())///using for token expire
            addNetworkInterceptor(FaceInterceptor())
            //addInterceptor(StethoInterceptor())//SupportInterceptor()
        }.build()

        fun getClientWithToken(): Retrofit {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl())
                .client(clientWithToken)
                .addConverterFactory(converter)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
            return retrofit
        }
    }
}