package com.kyi.openfoodfactsapi.models

import JsonHelper
import com.kyi.openfoodfactsapi.sources.JsonObject

class ProductTag(
    val barcode: String,
    val key: String,
    val value: String,
    val owner: String,
    val version: Int,
    val editor: String,
    val lastEdit: DateTime,
    val comment: String
) : JsonObject() {

    override fun toJson(): Map<String, Any?> = mapOf(
        "product" to barcode,
        "k" to key,
        "v" to value,
        "owner" to owner,
        "version" to version,
        "editor" to editor,
        "last_edit" to lastEdit.toIso8601String(),
        "comment" to comment
    )

    companion object {

        fun fromJson(json: Map<String, Any?>): ProductTag = ProductTag(
            barcode = json["product"] as String,
            key = json["k"] as String,
            value = json["v"] as String,
            owner = json["owner"] as String,
            version = (json["version"] as Number).toInt(),
            editor = json["editor"] as String,
            lastEdit = JsonHelper.stringTimestampToDate(json["last_edit"])!!,
            comment = json["comment"] as String
        )
    }

    override fun toString(): String = toJson().toString()
}