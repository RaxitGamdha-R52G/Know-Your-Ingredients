package com.kyi.openfoodfactsapi.utils


fun getUriReaderInstance(): UriReader = throw UnsupportedError("Cannot create the URI reader!")

class UnsupportedError(val value: String): Throwable(){
}
