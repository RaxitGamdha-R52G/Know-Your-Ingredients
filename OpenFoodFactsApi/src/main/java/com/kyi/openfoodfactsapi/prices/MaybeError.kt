package com.kyi.openfoodfactsapi.prices

import Response

class MaybeError<T> private constructor(private val value: T?, val isError: Boolean, val error: String?, val statusCode: Int?) {

    companion object {

        fun <T> value(value: T): MaybeError<T> = MaybeError(value, false, null, null)

        fun <T> responseError(response: Response): MaybeError<T> = MaybeError(null, true, utf8.decode(response.bodyBytes), response.statusCode)

        fun <T> unreadableResponse(response: Response): MaybeError<T> = MaybeError(null, false, utf8.decode(response.bodyBytes), response.statusCode)

        fun <T> error(error: String, statusCode: Int): MaybeError<T> = MaybeError(null, true, error, statusCode)
    }

    fun getValue(): T = value!!

    val detailError: String get() {
        return try {
            val json = jsonDecode(error!!) as Map<String, Any?>
            json["detail"] as String
        } catch (e: Exception) {
            error!!
        }
    }
}