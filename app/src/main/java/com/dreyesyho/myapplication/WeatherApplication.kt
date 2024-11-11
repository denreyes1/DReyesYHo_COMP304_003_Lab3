package com.dreyesyho.myapplication

import android.app.Application
import com.dreyesyho.myapplication.di.appModules
import org.koin.core.context.startKoin

class WeatherApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin{
            modules(appModules)
        }
    }
}