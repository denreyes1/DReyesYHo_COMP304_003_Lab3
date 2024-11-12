package com.dreyesyho.myapplication

import android.app.Application
import com.dreyesyho.myapplication.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin

class WeatherApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(applicationContext)
            modules(appModules)
            workManagerFactory()
        }
    }
}