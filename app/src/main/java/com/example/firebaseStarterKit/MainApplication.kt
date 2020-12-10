package com.example.firebaseStarterKit

import android.app.Application
import android.content.Context
import com.example.firebaseStarterKit.di.MainModule
import com.example.firebaseStarterKit.di.ViewModelModule
import com.google.android.gms.security.ProviderInstaller
import com.google.android.play.core.missingsplits.MissingSplitsManagerFactory
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import java.util.*

class MainApplication : Application() {
    override fun onCreate() {
        if (MissingSplitsManagerFactory.create(this).disableAppIfMissingRequiredSplits()) {
            // Skip app initialization.
            return
        }
        super.onCreate()

        //koin
        val moduleList = listOf(MainModule, ViewModelModule)
        startKoin() {
            androidLogger(Level.ERROR)
            // declare used Android context
            androidContext(this@MainApplication)
            // declare modules
            modules(moduleList)
        }

        try {
            ProviderInstaller.installIfNeeded(this)
        } catch (ignored: Exception) {
        }

        appContext = this
    }

    companion object {
        lateinit var appContext: Context
    }
}