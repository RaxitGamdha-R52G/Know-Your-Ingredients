package com.kyi.openfoodfactsapi.utils

class TooManyRequestsException : Exception() {

    override fun toString(): String = "Too many requests"

    companion object {

        private const val TOO_MANY_REQUESTS_ERROR = "<!DOCTYPE html><html><head><meta name=\"robots\" content=\"noindex\"></head><body><h1>TOO MANY REQUESTS</h1>"

        fun check(response: Response) {
            if (response.body.startsWith(TOO_MANY_REQUESTS_ERROR)) throw TooManyRequestsException()
        }
    }
}