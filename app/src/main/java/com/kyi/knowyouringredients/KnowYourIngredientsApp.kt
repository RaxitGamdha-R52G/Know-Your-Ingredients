package com.kyi.knowyouringredients

import android.app.Application
import com.kyi.knowyouringredients.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class KnowYourIngredientsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@KnowYourIngredientsApp)
            androidLogger()

            modules(appModule)
        }
    }
}