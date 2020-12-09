package com.example.firebaseStarterKit.di

import androidx.preference.PreferenceManager
import androidx.room.Room
import com.atmaneuler.hsdps.data.local.AppDatabase
import com.atmaneuler.hsdps.data.remote.RemoteRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val MainModule = module {
    single {
        RemoteRepository()
    }

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java, "hsdps.db"
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    single {
        PreferenceManager.getDefaultSharedPreferences(androidApplication())
    }
/*
    single {
        LoginUseCase(get(), get(), get())
    }*/
}