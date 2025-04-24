package com.example.eventshub

import android.app.Application
import com.example.eventshub.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class EventshubApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@EventshubApp)
            modules(appModule)
        }
    }
}

