package com.kyi.knowyouringredients.core.data.networking

import com.kyi.knowyouringredients.BuildConfig
import com.kyi.knowyouringredients.core.domain.util.NetworkError
import com.kyi.knowyouringredients.core.domain.util.Result

fun constructUrl(url: String): Result<String, NetworkError> {
    if (url.isBlank()) {
        return Result.Error(NetworkError.INVALID_URL)
    }
    val baseUrl = BuildConfig.BASE_URL.trimEnd('/')
    val trimmedUrl = url.trim()
    return when {
        trimmedUrl.startsWith(baseUrl) -> Result.Success(trimmedUrl)
        trimmedUrl.startsWith("/") -> {
            val cleanPath = trimmedUrl.dropWhile { it == '/' }
            if (cleanPath.isEmpty()) Result.Success(baseUrl) else Result.Success("$baseUrl/$cleanPath")
        }

        else -> Result.Success("$baseUrl/$trimmedUrl")
    }
}