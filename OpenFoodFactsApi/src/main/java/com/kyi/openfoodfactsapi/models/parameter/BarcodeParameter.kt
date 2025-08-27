package com.kyi.openfoodfactsapi.models.parameter


import com.kyi.openfoodfactsapi.sources.Parameter

/**
 * "Barcodes" search API parameter.
 */
class BarcodeParameter : Parameter {

    override fun getName(): String = "code"

    override fun getValue(): String = codes.joinToString(",")

    val codes: List<String>

    constructor(code: String) : this(listOf(code))

    constructor(codes: List<String>) {
        this.codes = codes
    }
}