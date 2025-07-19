package com.kyi.knowyouringredients.core.data.networking

import com.kyi.knowyouringredients.core.domain.util.NetworkError
import com.kyi.knowyouringredients.core.domain.util.Result
import com.kyi.knowyouringredients.ingredients.data.networking.dto.OneProductResponseDto
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.SerializationException

suspend inline fun <reified T> responseToResult(
    response: HttpResponse
): Result<T, NetworkError> {
    return when (response.status.value) {
        in 200..299 -> {
            try {
                val body = response.body<T>()
                if(body is OneProductResponseDto && (body.status != 1 || body.product == null)){
                    Result.Error(NetworkError.NOT_FOUND)
                }else{
                    Result.Success(body)
                }


            } catch (e: SerializationException) {
                Result.Error(NetworkError.SERIALIZATION) // Catch malformed JSON
            } catch (e: NoTransformationFoundException) {
                Result.Error(NetworkError.SERIALIZATION) // Catch transformation issues
            } catch (e: Exception) {
                Result.Error(NetworkError.UNKNOWN) // Catch unexpected errors
            }
        }

        400 -> Result.Error(NetworkError.BAD_REQUEST)
        401 -> Result.Error(NetworkError.UNAUTHORIZED)
        403 -> Result.Error(NetworkError.FORBIDDEN)
        404 -> Result.Error(NetworkError.NOT_FOUND)
        408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
        429 -> Result.Error(NetworkError.TOO_MANY_REQUESTS)
        in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
        else -> Result.Error(NetworkError.UNKNOWN)
    }
}