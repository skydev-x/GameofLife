package com.skydev.gameoflife

import android.app.Application
import di.initKoin
import org.koin.android.ext.koin.androidContext

class GOFAndroidApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@GOFAndroidApplication)
        }
    }

}