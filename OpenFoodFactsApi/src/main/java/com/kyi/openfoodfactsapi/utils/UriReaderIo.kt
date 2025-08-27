package com.kyi.openfoodfactsapi.utils


class UriReaderIo : UriReader() {

    override suspend fun readFileAsBytes(uri: Uri): List<Int> = File.fromUri(uri).readBytes().toList()
}