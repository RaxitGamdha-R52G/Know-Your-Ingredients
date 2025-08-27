package com.kyi.openfoodfactsapi.prices

import com.kyi.openfoodfactsapi.sources.JsonObject

class GetUsersResult(
    val items: List<PriceUser>? = null,
    val total: Int? = null,
    val pageNumber: Int? = null,
    val pageSize: Int? = null,
    val numberOfPages: Int? = null
) : JsonObject() {

    companion object {

        fun fromJson(json: Map<String, Any?>): GetUsersResult = GetUsersResult(
            items = (json["items"] as List<*>?)?.map { PriceUser.fromJson(it as Map<String, Any?>) },
            total = json["total"] as Int?,
            pageNumber = json["page"] as Int?,
            pageSize = json["size"] as Int?,
            numberOfPages = json["pages"] as Int?
        )
    }

    override fun toJson(): Map<String, Any?> = mapOf(
        "items" to items,
        "total" to total,
        "page" to pageNumber,
        "size" to pageSize,
        "pages" to numberOfPages
    )
}