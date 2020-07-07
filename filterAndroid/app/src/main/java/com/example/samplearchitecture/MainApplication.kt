package com.example.samplearchitecture

import android.app.Application
import com.example.data.di.dataModule
import com.example.domain.domainModule
import com.example.samplearchitecture.di.actorsModule
import com.example.samplearchitecture.di.appModule
import org.koin.android.ext.android.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(
            this, listOf(
                dataModule,
                domainModule,
                actorsModule,
                appModule
            )
        )
    }

}