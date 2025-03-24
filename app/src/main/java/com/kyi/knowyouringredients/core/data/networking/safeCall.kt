package com.kyi.knowyouringredients.core.data.networking

import com.kyi.knowyouringredients.core.domain.util.NetworkError
import com.kyi.knowyouringredients.core.domain.util.Result
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T> safeCall(
    urlProvider: () -> Result<String, NetworkError>,
    crossinline execute: suspend (String) -> HttpResponse
): Result<T, NetworkError> {
    val urlResult = urlProvider()
    return when (urlResult) {
        is Result.Success -> {
            val response: HttpResponse = try {
                execute(urlResult.data)
            } catch (e: UnresolvedAddressException) {
                return Result.Error(NetworkError.NO_INTERNET)
            } catch (e: SerializationException) {
                return Result.Error(NetworkError.SERIALIZATION)
            } catch (e: HttpRequestTimeoutException) {
                return Result.Error(NetworkError.REQUEST_TIMEOUT) // Specific timeout handling
            } catch (e: Exception) {
                coroutineContext.ensureActive()
                return Result.Error(NetworkError.UNKNOWN) // Fallback for other errors
            }
            responseToResult(response)
        }

        is Result.Error -> urlResult
    }
}