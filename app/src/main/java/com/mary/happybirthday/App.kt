package com.mary.happybirthday

import android.app.Application
import com.mary.happybirthday.di.featuresModule
import com.mary.happybirthday.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(appModule, featuresModule)
        }
        if(BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }
}