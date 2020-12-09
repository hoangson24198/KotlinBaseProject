package com.atmaneuler.hsdps.data.remote

import androidx.preference.PreferenceManager
import com.atmaneuler.hsdps.MainApplication
import com.atmaneuler.hsdps.base.Constant
import com.atmaneuler.hsdps.data.local.LUser
import com.google.gson.Gson
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import timber.log.Timber


class TokenAuthenticator : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val pref = PreferenceManager.getDefaultSharedPreferences(MainApplication.appContext)
        val value = pref.getString(com.atmaneuler.hsdps.base.Constant.KEY_USER_CREDENTIALS, "")

        if (!(response.code == 401) && (response.request.header("Authorization") != null
                    || !response.request.header("Authorization").equals("Bearer " + value))
        ) {
            //returning null means we have tried and the refresh failed so exit; this will also get you out of infinite loop resulting from retrying
            return null
        }

        value?.let {//update token and save to pref
            val user = Gson().fromJson(value, LUser::class.java)

            Timber.d("ABCD old teken ${user.token}")
            Timber.d("ABCD old refresh teken ${user.refresh_token}")

            val api = ApiInterface.getClient().create(ApiToken::class.java)
            val newToken = api.refreshtoken(user.token, user.refresh_token).blockingGet()

            user.token = newToken.data?.token ?: ""
            user.refresh_token = newToken.data?.refesh_token ?: ""

            Timber.d("ABCD new teken ${newToken.data?.token ?: ""}")
            Timber.d("ABCD new  refresh teken ${newToken.data?.refesh_token ?: ""}")

            if (!user.token.isNullOrEmpty() && !user.refresh_token.isNullOrEmpty())
                pref.edit().putString(Constant.KEY_USER_CREDENTIALS, Gson().toJson(user))
                    .commit()


            return if (!user.token.isNullOrEmpty()) {//nếu có token new
                val newAccess = "Bearer ${user.token}" //new token
                // make current request with new access token
                response.request.newBuilder()
                    .header("Authorization", newAccess)
                    .build()
            } else {
                //fail return login screen
                null
            }


        }
        return null
    }
}