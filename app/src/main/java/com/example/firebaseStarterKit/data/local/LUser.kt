package com.atmaneuler.hsdps.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "luser")
class LUser {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @ColumnInfo(name = "token")
    var token: String = ""

    @ColumnInfo(name = "refresh_token")
    var refresh_token: String = ""

    @ColumnInfo(name = "username")//!
    var username: String = ""

    @ColumnInfo(name = "password")//
    var password: String = ""

    @ColumnInfo(name = "nfc")//!
    var nfc: String = ""

    @ColumnInfo(name = "user_info")
    var user_info: String = ""

    @ColumnInfo(name = "userId")//must equal network field
    var remoteUserId: String = ""

    @ColumnInfo(name = "isLogged")//!
    var isLogged: Boolean = false

    @ColumnInfo(name = "unique")
    var unique: String = ""

    @ColumnInfo(name = "logout_time")
    var logout_time: Long = 10
}

