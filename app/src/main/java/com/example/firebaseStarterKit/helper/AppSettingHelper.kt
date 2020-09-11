package com.atmaneuler.hsdps.helper

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

class AppSettingHelper(
    private val context: Context
) {

    fun generalSettings() {
        val intent = Intent(Settings.ACTION_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }

    fun homeSettings() {
        val intent = Intent(Settings.ACTION_HOME_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }

    fun appDetailsSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        intent.data = Uri.parse("package:" + context.packageName)
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }

    fun nfcSettings() {
        val intent = Intent(Settings.ACTION_NFC_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }

    fun wifiSettings() {
        val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }

    fun locationSourceSettings() {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }

    fun wirelessSettings() {
        val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }

    fun airplaneModeSettings() {
        val intent = Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }

    fun apnSettings() {
        val intent = Intent(Settings.ACTION_APN_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }


    fun bluetoothSettings() {
        val intent = Intent(Settings.ACTION_BLUETOOTH_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }

    fun dateSettings() {
        val intent = Intent(Settings.ACTION_DATE_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }

    fun localeSettings() {
        val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }

    fun inputMethodSettings() {
        val intent = Intent(Settings.ACTION_INPUT_METHOD_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }

    fun displaySettings() {
        val intent = Intent(Settings.ACTION_DISPLAY_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }

    fun securitySettings() {
        val intent = Intent(Settings.ACTION_SECURITY_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }

    fun internalStorageSettings() {
        val intent = Intent(Settings.ACTION_INTERNAL_STORAGE_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }

    fun memoryCardSettings() {
        val intent = Intent(Settings.ACTION_MEMORY_CARD_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }

    fun accessibilitySettings() {
        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }

    fun applicationSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }

    fun deviceInfoSettings() {
        val intent = Intent(Settings.ACTION_DEVICE_INFO_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }

    fun appNotificationSettings() {
        val intent =
            Intent("android.settings.APP_NOTIFICATION_SETTINGS") // Settings.ACTION_APP_NOTIFICATION_SETTINGS
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)

        //for Android 5-7
        intent.putExtra("app_package", context.packageName)
        intent.putExtra("app_uid", context.applicationInfo.uid)

        // for Android 8 and above
        intent.putExtra(
            "android.provider.extra.APP_PACKAGE",
            context.packageName
        ) // Settings.EXTRA_APP_PACKAGE
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }
}