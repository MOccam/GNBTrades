package com.mperezc.gnbtrades

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class GNBTradesApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        //Initialize Koin
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@GNBTradesApplication)
            modules(appModules)
        }
    }
}