package com.kyi.openfoodfactsapi.models

import JsonHelper
import com.kyi.openfoodfactsapi.sources.JsonObject

class ProductStats(
    val barcode: String,
    val numberOfKeys: Int,
    val numberOfEditors: Int,
    val lastEdit: DateTime
) : JsonObject() {

    override fun toJson(): Map<String, Any?> = mapOf(
        "product" to barcode,
        "keys" to numberOfKeys,
        "editors" to numberOfEditors,
        "last_edit" to lastEdit.toIso8601String()
    )

    companion object {

        fun fromJson(json: Map<String, Any?>): ProductStats = ProductStats(
            barcode = json["product"] as String,
            numberOfKeys = (json["keys"] as Number).toInt(),
            numberOfEditors = (json["editors"] as Number).toInt(),
            lastEdit = JsonHelper.stringTimestampToDate(json["last_edit"])!!
        )
    }

    override fun toString(): String = toJson().toString()
}