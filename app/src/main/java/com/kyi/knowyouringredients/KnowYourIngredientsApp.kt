package com.kyi.knowyouringredients

import android.app.Application
import com.kyi.knowyouringredients.di.appModule
import com.kyi.knowyouringredients.di.authModule
import com.kyi.knowyouringredients.di.preferencesModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class KnowYourIngredientsApp : Application() {

    override fun onCreate() {
        super.onCreate()

//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        startKoin {
            androidContext(this@KnowYourIngredientsApp)
            androidLogger()

            modules(
                listOf(
                    appModule,
                    authModule,
                    preferencesModule
                )
            )
        }
    }
}