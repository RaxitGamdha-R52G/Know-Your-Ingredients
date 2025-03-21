package com.kyi.knowyouringredients.core.core.networking

import com.kyi.knowyouringredients.core.domain.util.NetworkError
import com.kyi.knowyouringredients.core.domain.util.Result
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): Result<T, NetworkError> {
    val response = try {
        execute()
    } catch (e: UnresolvedAddressException) {
        return Result.Error(NetworkError.NO_INTERNET)
    } catch (e: SerializationException) {
        return Result.Error(NetworkError.SERIALIZATION)
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        return Result.Error(NetworkError.UNKNOWN)
    }

    return responseToResult(response)
}

//
//package com.kyi.knowyouringredients.core.core.networking
//
//import com.kyi.knowyouringredients.core.domain.util.NetworkError
//import com.kyi.knowyouringredients.core.domain.util.Result
//import io.ktor.client.statement.HttpResponse
//import io.ktor.util.network.UnresolvedAddressException
//import io.ktor.utils.io.errors.IOException
//import kotlinx.coroutines.CancellationException
//import kotlinx.coroutines.ensureActive
//import java.util.concurrent.TimeoutException
//import kotlin.coroutines.coroutineContext
//
//suspend inline fun <reified T> safeCall(
//    execute: () -> HttpResponse,
//    successRange: IntRange = 200..299,
//    logger: ((String) -> Unit)? = null // Optional logger for debugging
//): Result<T, NetworkError> {
//    val response = try {
//        execute()
//    } catch (e: UnresolvedAddressException) {
//        logger?.invoke("No internet connection: ${e.message}")
//        return Result.Error(NetworkError.NO_INTERNET)
//    } catch (e: TimeoutException) {
//        logger?.invoke("Request timed out: ${e.message}")
//        return Result.Error(NetworkError.REQUEST_TIMEOUT)
//    } catch (e: IOException) {
//        logger?.invoke("Network error: ${e.message}")
//        return Result.Error(NetworkError.NETWORK_ERROR)
//    } catch (e: CancellationException) {
//        logger?.invoke("Request cancelled: ${e.message}")
//        throw e // Propagate cancellation to caller
//    } catch (e: Exception) {
//        coroutineContext.ensureActive() // Check if cancelled, but don’t mask other errors
//        logger?.invoke("Unknown error during request: ${e.message}")
//        return Result.Error(NetworkError.UNKNOWN)
//    }
//
//    return responseToResult(response, successRange, logger)
//}