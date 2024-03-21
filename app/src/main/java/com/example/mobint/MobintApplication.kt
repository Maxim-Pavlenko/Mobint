package com.example.mobint

import android.app.Application
import com.example.mobint.di.appModule
import com.example.mobint.di.dataBaseModule
import com.example.mobint.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MobintApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MobintApplication)
            modules(appModule, dataModule, dataBaseModule)
        }
    }
}