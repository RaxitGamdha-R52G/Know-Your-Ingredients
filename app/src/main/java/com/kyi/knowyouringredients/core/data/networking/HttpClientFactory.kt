package com.kyi.knowyouringredients.core.data.networking

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientFactory {
    fun create(engine: HttpClientEngine): HttpClient {
        return HttpClient(engine) {
            install(Logging) {
                level = LogLevel.ALL
                logger = Logger.ANDROID
            }
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true // For readable logs
                        isLenient = true // Handle non-strict JSON
                        ignoreUnknownKeys = true // Skip unknown fields
                        encodeDefaults = true // Ensure default values are serialized
                    }
                )
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 15_000 // 15 seconds
                connectTimeoutMillis = 10_000 // 10 seconds
                socketTimeoutMillis = 10_000  // 10 seconds
            }

            defaultRequest {
                contentType(ContentType.Application.Json)
            }
        }
    }
}