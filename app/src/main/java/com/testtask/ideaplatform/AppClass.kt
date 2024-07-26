package com.testtask.ideaplatform

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppClass: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@AppClass)
            modules(appModule)
        }
    }
}