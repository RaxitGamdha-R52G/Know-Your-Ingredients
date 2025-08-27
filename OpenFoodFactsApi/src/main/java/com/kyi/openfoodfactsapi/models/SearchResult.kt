package com.kyi.openfoodfactsapi.models

import com.kyi.openfoodfactsapi.sources.JsonObject


class SearchResult(
    val page: Int? = null,
    val pageSize: Int? = null,
    val count: Int? = null,
    val pageCount: Int? = null,
    val skip: Int? = null,
    val products: List<Product>? = null
) : JsonObject() {

    override fun toJson(): Map<String, Any?> = mapOf(
        "page" to page,
        "page_size" to pageSize,
        "count" to count,
        "page_count" to pageCount,
        "skip" to skip,
        "products" to products
    )

    companion object {

        fun fromJson(json: Map<String, Any?>): SearchResult = SearchResult(
            page = parseInt(json["page"]),
            pageSize = parseInt(json["page_size"]),
            count = parseInt(json["count"]),
            pageCount = parseInt(json["page_count"]),
            skip = parseInt(json["skip"]),
            products = (json["products"] as List<*>?)?.map { Product.fromJson(it as Map<String, Any?>) }
        )
    }
}