package com.kyi.openfoodfactsapi.utils

import android.net.Uri


abstract class UriReader {

    companion object {

        val instance: UriReader get() = _instance ?: getUriReaderInstance().also { _instance = it }

        private var _instance: UriReader? = null

        private var _initialized = false
    }

//    suspend fun readAsBytes(uri: Uri): List<Int> {
//        uri.data?.contentAsBytes()?.let { return it.toList() }
//        return when (uri.scheme) {
//            "", "file" -> readFileAsBytes(uri)
//            "http", "https" -> {
//                val response = http.get(uri)
//                response.bodyBytes.toList()
//            }
//            else -> throw Exception("Unknown uri scheme for $uri")
//        }
//    }
suspend fun readAsBytes(uri: Uri): ByteArray {
    // CORRECTED: Check the scheme to handle different Uri types.
    return when (uri.scheme) {
//        "data" -> {
//            // CORRECTED: Proper handling for Data URIs.
//            val schemeSpecificPart = uri.schemeSpecificPart
//            val commaIndex = schemeSpecificPart.indexOf(',')
//            if (commaIndex == -1) {
//                throw IllegalArgumentException("Malformed data URI: $uri")
//            }
//            val encodedData = schemeSpecificPart.substring(commaIndex + 1)
//
//            // Check if the data is Base64 encoded.
//            if (schemeSpecificPart.substring(0, commaIndex).contains(";base64", ignoreCase = true)) {
//                Base64.decode(encodedData, Base64.DEFAULT)
//            } else {
//                // For now, assume url-encoded data is not handled, but could be added.
//                // URLDecoder.decode(encodedData, "UTF-8").toByteArray()
//                throw IllegalArgumentException("Unsupported data URI encoding for: $uri")
//            }
//        }
        "file", "" -> readFileAsBytes(uri)
        "http", "https" -> {
            val response = http.get(uri.toString()) // Pass the string representation
            response.bodyBytes
        }
        else -> throw IllegalArgumentException("Unknown or unsupported uri scheme for $uri")
    }
}

    abstract suspend fun readFileAsBytes(uri: Uri): List<Int>

    open val isWeb: Boolean get() = false // Adjust for Kotlin Multiplatform if needed

}