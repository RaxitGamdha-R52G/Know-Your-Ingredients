package com.kyi.knowyouringredients.core.presentation.util

import android.content.Context
import com.kyi.knowyouringredients.R
import com.kyi.knowyouringredients.core.domain.util.NetworkError

fun NetworkError.toString(context: Context): String {
    val resId = when (this) {
        NetworkError.BAD_REQUEST -> R.string.bad_request
        NetworkError.UNAUTHORIZED -> R.string.unauthorized
        NetworkError.FORBIDDEN -> R.string.forbidden
        NetworkError.NOT_FOUND -> R.string.not_found
        NetworkError.REQUEST_TIMEOUT -> R.string.request_timeout
        NetworkError.TOO_MANY_REQUESTS -> R.string.too_many_requests
        NetworkError.NO_INTERNET -> R.string.no_internet
//        NetworkError.SERVER_ERROR -> R.string.server_error
        NetworkError.SERVER_ERROR -> R.string.unknown_error
        NetworkError.SERIALIZATION -> R.string.serialization_error
        NetworkError.UNKNOWN -> R.string.unknown_error
        NetworkError.INVALID_URL -> R.string.invalid_url
    }
    return context.getString(resId)
}