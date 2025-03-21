package com.kyi.knowyouringredients.core.data.networking

import com.kyi.knowyouringredients.BuildConfig

fun constructUrl(url: String): String {
    return when {
        url.contains(BuildConfig.BASE_URL) -> url
        url.startsWith("/") -> BuildConfig.BASE_URL + url.drop(1)
        else -> BuildConfig.BASE_URL + url
    }
}

//package com.kyi.knowyouringredients.core.core.networking
//
//import com.kyi.knowyouringredients.BuildConfig
//
//fun constructUrl(url: String): String {
//    // Return empty string or throw exception for invalid input
//    if (url.isBlank()) {
//        return "" // Or throw IllegalArgumentException("URL cannot be blank")
//    }
//
//    // Normalize base URL and input URL
//    val baseUrl = BuildConfig.BASE_URL.trimEnd('/')
//    val trimmedUrl = url.trim()
//
//    return when {
//        // If the URL already contains the base URL as a prefix, return it unchanged
//        trimmedUrl.startsWith(baseUrl) -> trimmedUrl
//
//        // If the URL starts with a slash, append it to base URL after dropping extra slashes
//        trimmedUrl.startsWith("/") -> {
//            val cleanPath = trimmedUrl.dropWhile { it == '/' }
//            if (cleanPath.isEmpty()) baseUrl else "$baseUrl/$cleanPath"
//        }
//
//        // For relative paths without a leading slash, append with a single slash
//        else -> "$baseUrl/$trimmedUrl"
//    }
//}