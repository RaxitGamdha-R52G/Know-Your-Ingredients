package com.kyi.openfoodfactsapi.models

import AndroidUri
import ImageField
import OpenFoodFactsLanguage
import com.kyi.openfoodfactsapi.sources.JsonObject

class SendImage(
    var lang: OpenFoodFactsLanguage? = null,
    val imageUri: AndroidUri,
    val barcode: String,
    val imageField: ImageField = ImageField.OTHER
) : JsonObject() {

    fun getImageDataKey(): String {
        var imageDataKey = "imgupload_${imageField.offTag}"
        if (lang != null) imageDataKey += "_${lang!!.offTag}"
        return imageDataKey
    }

    private fun getImageFieldWithLang(): String {
        var imageFieldWithLang = imageField.offTag
        if (lang != null) imageFieldWithLang += "_${lang!!.offTag}"
        return imageFieldWithLang
    }

    override fun toJson(): Map<String, Any?> = mapOf(
        "lc" to lang?.code,
        "code" to barcode,
        "imagefield" to getImageFieldWithLang()
    )
}