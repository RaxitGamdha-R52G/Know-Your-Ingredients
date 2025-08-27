package com.kyi.openfoodfactsapi.utils

class InvalidBarcodes(invalidBarcodesString: String) {

    private val barcodes = mutableListOf<String>()

    init {
        val inputJson = HttpHelper.jsonDecode(invalidBarcodesString)
        for (item in inputJson as List<*>) {
            barcodes.add(item as String)
        }
        if (barcodes.isEmpty()) {
            throw Exception("Unexpected error: empty invalid barcode list from json string $invalidBarcodesString")
        }
    }

    constructor() : this("") {
        barcodes.addAll(INVALID_BARCODES)
    }

    fun isBlacklisted(barcode: String): Boolean = barcodes.contains(barcode)

    companion object {

        fun getUrl(): String = "https://world.openfoodfacts.org/data/invalid-barcodes.json"

        private val INVALID_BARCODES = listOf(
            "323673",
            "2575405",
            "10232903",
            "10576403",
            "10836813",
            "12562213",
            "13297703",
            "15600703",
            "16130357",
            "16256163",
            "16256866"
        )
    }
}