package com.kyi.knowyouringredients.di

import android.content.Context
import com.kyi.knowyouringredients.ingredients.data.preferences.PreferenceDataSource
import org.koin.dsl.module

val preferencesModule = module {
    single { PreferenceDataSource(get<Context>()) }
}