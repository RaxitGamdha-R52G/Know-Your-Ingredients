package com.kyi.openfoodfactsapi.utils


class UriReaderJs : UriReader() {

    override suspend fun readFileAsBytes(uri: Uri): List<Int> = throw Exception("Cannot read files in web version")

    override val isWeb: Boolean get() = true
}