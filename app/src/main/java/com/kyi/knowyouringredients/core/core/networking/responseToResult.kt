package com.kyi.knowyouringredients.core.core.networking

import com.kyi.knowyouringredients.core.domain.util.NetworkError
import com.kyi.knowyouringredients.core.domain.util.Result
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

suspend inline fun <reified T> responseToResult(
    response: HttpResponse
): Result<T, NetworkError> {
    return when (response.status.value) {
        in 200..299 -> {
            try {
                Result.Success(response.body<T>())
            } catch (e: NoTransformationFoundException) {
                Result.Error(NetworkError.SERIALIZATION)
            }
        }

        400 -> Result.Error(NetworkError.BAD_REQUEST)       // Client error
        401 -> Result.Error(NetworkError.UNAUTHORIZED)      // Authentication failure
        403 -> Result.Error(NetworkError.FORBIDDEN)         // Access denied
        404 -> Result.Error(NetworkError.NOT_FOUND)         // Resource Missing
        408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
        429 -> Result.Error(NetworkError.TOO_MANY_REQUESTS)
        in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
        else -> Result.Error(NetworkError.UNKNOWN)
    }
}

//
//package com.kyi.knowyouringredients.core.core.networking
//
//import com.kyi.knowyouringredients.core.domain.util.NetworkError
//import com.kyi.knowyouringredients.core.domain.util.Result
//import io.ktor.client.call.NoTransformationFoundException
//import io.ktor.client.call.body
//import io.ktor.client.statement.HttpResponse
//import io.ktor.utils.io.errors.IOException
//import kotlinx.serialization.SerializationException
//
//suspend inline fun <reified T> responseToResult(
//    response: HttpResponse,
//    successRange: IntRange = 200..299,
//    logger: ((String) -> Unit)? = null // Optional logger for debugging
//): Result<T, NetworkError> {
//    val statusCode = response.status.value
//    return when (statusCode) {
//        in successRange -> {
//            try {
//                Result.Success(response.body<T>())
//            } catch (e: NoTransformationFoundException) {
//                logger?.invoke("Serialization failed: No transformation found - ${e.message}")
//                Result.Error(NetworkError.SERIALIZATION)
//            } catch (e: SerializationException) {
//                logger?.invoke("Serialization failed: Invalid data - ${e.message}")
//                Result.Error(NetworkError.SERIALIZATION)
//            } catch (e: Exception) {
//                logger?.invoke("Unexpected error during response parsing: ${e.message}")
//                Result.Error(NetworkError.UNKNOWN)
//            }
//        }
//
//        400 -> Result.Error(NetworkError.BAD_REQUEST)
//        401 -> Result.Error(NetworkError.UNAUTHORIZED)
//        402 -> Result.Error(NetworkError.PAYMENT_REQUIRED)
//        403 -> Result.Error(NetworkError.FORBIDDEN)
//        404 -> Result.Error(NetworkError.NOT_FOUND)
//        405 -> Result.Error(NetworkError.METHOD_NOT_ALLOWED)
//        406 -> Result.Error(NetworkError.NOT_ACCEPTABLE)
//        408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
//        409 -> Result.Error(NetworkError.CONFLICT)
//        410 -> Result.Error(NetworkError.GONE)
//        429 -> Result.Error(NetworkError.TOO_MANY_REQUESTS)
//        in 500..599 -> {
//            val errorMessage = try {
//                response.body<String>()
//            } catch (e: Exception) {
//                "Unknown server error"
//            }
//            logger?.invoke("Server error ($statusCode): $errorMessage")
//            Result.Error(NetworkError.SERVER_ERROR)
//        }
//        else -> {
//            logger?.invoke("Unhandled status code: $statusCode")
//            Result.Error(NetworkError.UNKNOWN)
//        }
//    }
//}