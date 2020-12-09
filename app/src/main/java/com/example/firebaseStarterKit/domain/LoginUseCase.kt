package com.example.firebaseStarterKit.domain

import android.content.SharedPreferences
import com.atmaneuler.hsdps.base.Constant
import com.atmaneuler.hsdps.data.local.AppDatabase
import com.atmaneuler.hsdps.data.local.LUser
import com.atmaneuler.hsdps.data.remote.RemoteRepository
import com.atmaneuler.hsdps.helper.decodeBase64
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.reactivex.Observable
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginUseCase(
    val repo: RemoteRepository,
    val local: AppDatabase,
    val pref: SharedPreferences
) {

    /*fun login(user: JsonObject): Observable<ResponseBase<ResponseUser>> {
        return repo.api.loginUsernamPassword(user)
    }*/


    fun writeToLocal(user: LUser): Boolean {
        GlobalScope.launch {
            var existUser = local.LUserDao().findByUsername(user.username)
            if (existUser != null) {
                existUser.unique = System.currentTimeMillis().toString()
                existUser.token = user.token
                existUser.refresh_token = user.refresh_token
                existUser.user_info = user.user_info
                local.LUserDao().update(existUser)
            } else {
                user.unique = System.currentTimeMillis().toString()
                existUser = user
                local.LUserDao().insertUser(user)
            }

            pref.edit().putString(Constant.KEY_USER_CREDENTIALS, Gson().toJson(existUser)).apply()
            pref.edit().putString(Constant.KEY_TIMEOUT_LOGOUT, user.logout_time.toString())
                .apply()//0.01//2p

            pref.edit().putString(
                Constant.KEY_USER_INFO,
                existUser.user_info.toString().decodeBase64()
            ).apply()
        }
        return true
    }
}