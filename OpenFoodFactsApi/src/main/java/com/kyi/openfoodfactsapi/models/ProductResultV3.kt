package com.kyi.openfoodfactsapi.models

import com.kyi.openfoodfactsapi.sources.JsonObject


class ProductResultV3 : JsonObject() {

    var barcode: String? = null

    var result: LocalizedTag? = null

    var status: String? = null

    var errors: List<ProductResultFieldAnswer>? = null

    var warnings: List<ProductResultFieldAnswer>? = null

    var product: Product? = null

    companion object {

        const val STATUS_FAILURE = "failure"
        const val STATUS_WARNING = "success_with_warnings"
        const val STATUS_SUCCESS = "success"

        const val RESULT_PRODUCT_FOUND = "product_found"
        const val RESULT_PRODUCT_NOT_FOUND = "product_not_found"

        fun fromJson(json: Map<String, Any?>): ProductResultV3 = ProductResultV3().apply {
            barcode = json["code"] as String?
            result = json["result"]?.let { LocalizedTag.fromJson(it as Map<String, Any?>) }
            status = json["status"] as String?
            errors = fromJsonListAnswerForField(json["errors"])
            warnings = fromJsonListAnswerForField(json["warnings"])
            product = json["product"]?.let { Product.fromJson(it as Map<String, Any?>) }
        }

        private fun fromJsonListAnswerForField(list: Any?): List<ProductResultFieldAnswer>? {
            if (list == null) return null
            val result = mutableListOf<ProductResultFieldAnswer>()
            for (item in list as List<*>) {
                result.add(ProductResultFieldAnswer.fromJson(item as Map<String, Any?>))
            }
            return result
        }
    }

    override fun toJson(): Map<String, Any?> = mapOfNotNull(
        "code" to barcode,
        "result" to result,
        "status" to status,
        "errors" to errors,
        "warnings" to warnings,
        "product" to product
    )

    override fun toString(): String = toJson().toString()
}