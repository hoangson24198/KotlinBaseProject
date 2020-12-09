package com.atmaneuler.hsdps.data.remote

import androidx.preference.PreferenceManager
import com.atmaneuler.hsdps.MainApplication
import com.atmaneuler.hsdps.data.local.LUser
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import timber.log.Timber
import java.io.IOException

class SupportInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var newRequest = chain.request().newBuilder().build()
        return onOnIntercept(
            chain,
            newRequest
        )
    }

    @Throws(IOException::class)
    private fun onOnIntercept(chain: Interceptor.Chain, request: Request): Response {
        //chain.proceed(request)
        var myRequest = request
        val pref = PreferenceManager.getDefaultSharedPreferences(MainApplication.appContext)


        if (request.header("No-Authentication") == null) {
            val value = pref.getString(com.atmaneuler.hsdps.base.Constant.KEY_USER_CREDENTIALS, "")
            if (!value.isNullOrEmpty()) {
                val data = Gson().fromJson(value, LUser::class.java)
                if (!data.token.isNullOrBlank()) {
                    val finalToken = "Bearer ${data.token}"
                    myRequest = request.newBuilder()
                        .addHeader("Authorization", finalToken)
                        .addHeader("Accept", "application/json")
                        .build()
                }
            }
        }


        //val response = chain.proceed(request)
        val response = chain.proceed(myRequest)
        Timber.d("ABCD onIntercerp $response")
        return response.newBuilder().body(response.body).code(200).build()
    }
}
