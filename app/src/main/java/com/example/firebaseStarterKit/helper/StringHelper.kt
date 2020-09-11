package com.atmaneuler.hsdps.helper

import android.os.Build

fun String.decodeBase64(): String {
    val decodedBytes = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        java.util.Base64.getDecoder().decode(this)
    } else {
        return ""
    }
    val decodedString = String(decodedBytes)
    return decodedString
}