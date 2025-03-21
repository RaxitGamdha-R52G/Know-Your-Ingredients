package com.kyi.knowyouringredients.core.data.networking

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
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
                    json = Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
            }
        }
    }
}

//package com.kyi.knowyouringredients.core.core.networking
//
//import io.ktor.client.HttpClient
//import io.ktor.client.engine.HttpClientEngine
//import io.ktor.client.plugins.HttpTimeout
//import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
//import io.ktor.client.plugins.defaultRequest
//import io.ktor.client.plugins.logging.LogLevel
//import io.ktor.client.plugins.logging.Logger
//import io.ktor.client.plugins.logging.Logging
//import io.ktor.http.ContentType
//import io.ktor.http.contentType
//import io.ktor.serialization.kotlinx.json.json
//import kotlinx.serialization.json.Json
//
//object HttpClientFactory {
//    // Cache the HttpClient instance as a singleton (lazy initialization)
//    private val clientInstance: HttpClient? by lazy { null }
//
//    fun create(
//        engine: HttpClientEngine,
//        logLevel: LogLevel = if (BuildConfig.DEBUG) LogLevel.ALL else LogLevel.NONE,
//        logger: Logger = Logger.DEFAULT, // Platform-agnostic default
//        configureJson: Json.() -> Unit = { ignoreUnknownKeys = true },
//        requestTimeoutMillis: Long = 30_000, // 30 seconds
//        connectTimeoutMillis: Long = 10_000  // 10 seconds
//    ): HttpClient {
//        // Return cached instance if already created (optional, remove if not desired)
//        clientInstance?.let { return it }
//
//        return HttpClient(engine) {
//            // Logging configuration
//            install(Logging) {
//                level = logLevel
//                this.logger = logger
//            }
//
//            // JSON content negotiation
//            install(ContentNegotiation) {
//                json(
//                    json = Json {
//                        configureJson() // Apply custom JSON configuration
//                    }
//                )
//            }
//
//            // Timeout configuration
//            install(HttpTimeout) {
//                this.requestTimeoutMillis = requestTimeoutMillis
//                this.connectTimeoutMillis = connectTimeoutMillis
//                this.socketTimeoutMillis = connectTimeoutMillis
//            }
//
//            // Default request settings
//            defaultRequest {
//                // Only set content type for requests with a body (e.g., POST, PUT)
//                if (method in listOf(HttpMethod.Post, HttpMethod.Put, HttpMethod.Patch)) {
//                    contentType(ContentType.Application.Json)
//                }
//            }
//        }.also { clientInstance = it } // Cache the instance (optional)
//    }
//}