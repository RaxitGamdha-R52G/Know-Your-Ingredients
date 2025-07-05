package com.kyi.knowyouringredients.di

import android.content.Context
import coil.ImageLoader
import coil.disk.DiskCache
import org.koin.dsl.module

val imageModule = module {
    single {
        val context: Context = get()
        ImageLoader.Builder(context)
            .diskCache {
                DiskCache.Builder()
                    .directory(context.cacheDir.resolve("image_cache"))
                    .maxSizePercent(0.25) // Use 25% of app's cache space
                    .build()
            }
            .crossfade(true)
            .respectCacheHeaders(true)
            .build()
    }
}