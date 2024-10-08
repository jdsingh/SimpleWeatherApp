package me.jagdeep.playground.app

import android.app.Application
import me.jagdeep.playground.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WeatherApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WeatherApplication)
            modules(listOf(appModule))
        }
    }
}
