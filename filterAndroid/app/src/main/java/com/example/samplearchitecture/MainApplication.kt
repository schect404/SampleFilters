package com.example.samplearchitecture

import android.app.Application
import com.example.data.di.dataModule
import com.example.domain.domainModule
import com.example.samplearchitecture.di.actorsModule
import com.example.samplearchitecture.di.appModule
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        stopKoin()
        startKoin {
            androidContext(this@MainApplication.applicationContext)
            modules(listOf(dataModule, domainModule, actorsModule, appModule))
        }
    }

}