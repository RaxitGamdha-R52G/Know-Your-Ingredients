package com.kyi.openfoodfactsapi.prices

import com.kyi.openfoodfactsapi.sources.JsonObject
import com.kyi.openfoodfactsapi.utils.JsonHelper

class PriceProduct(
    var code: String,
    var priceCount: Int? = null,
    var locationCount: Int? = null,
    var userCount: Int? = null,
    var proofCount: Int? = null,
    var productId: Int,
    var source: Flavor? = null,
    var name: String? = null,
    var quantity: Int? = null,
    var quantityUnit: String? = null,
    var categoriesTags: List<String>,
    var brands: String? = null,
    var brandsTags: List<String>,
    var labelsTags: List<String>,
    var imageURL: String? = null,
    var nutriscoreGrade: String? = null,
    var ecoscoreGrade: String? = null,
    var novaGroup: Int? = null,
    var uniqueScansNumber: Int,
    var created: DateTime,
    var updated: DateTime? = null
) : JsonObject() {

    companion object {

        fun fromJson(json: Map<String, Any?>): PriceProduct = PriceProduct(
            code = json["code"] as String,
            priceCount = json["price_count"] as Int?,
            locationCount = json["location_count"] as Int?,
            userCount = json["user_count"] as Int?,
            proofCount = json["proof_count"] as Int?,
            productId = json["id"] as Int,
            source = Flavor.fromOffTag(json["source"] as String?),
            name = json["product_name"] as String?,
            quantity = json["product_quantity"] as Int?,
            quantityUnit = json["product_quantity_unit"] as String?,
            categoriesTags = (json["categories_tags"] as List<*>).map { it as String },
            brands = json["brands"] as String?,
            brandsTags = (json["brands_tags"] as List<*>).map { it as String },
            labelsTags = (json["labels_tags"] as List<*>).map { it as String },
            imageURL = json["image_url"] as String?,
            nutriscoreGrade = json["nutriscore_grade"] as String?,
            ecoscoreGrade = json["ecoscore_grade"] as String?,
            novaGroup = json["nova_group"] as Int?,
            uniqueScansNumber = json["unique_scans_n"] as Int,
            created = JsonHelper.stringTimestampToDate(json["created"] as String),
            updated = JsonHelper.nullableStringTimestampToDate(json["updated"] as String?)
        )
    }

    override fun toJson(): Map<String, Any?> = mapOf(
        "code" to code,
        "price_count" to priceCount,
        "location_count" to locationCount,
        "user_count" to userCount,
        "proof_count" to proofCount,
        "id" to productId,
        "source" to source?.offTag,
        "product_name" to name,
        "product_quantity" to quantity,
        "product_quantity_unit" to quantityUnit,
        "categories_tags" to categoriesTags,
        "brands" to brands,
        "brands_tags" to brandsTags,
        "labels_tags" to labelsTags,
        "image_url" to imageURL,
        "nutriscore_grade" to nutriscoreGrade,
        "ecoscore_grade" to ecoscoreGrade,
        "nova_group" to novaGroup,
        "unique_scans_n" to uniqueScansNumber,
        "created" to created.toIso8601String(),
        "updated" to updated?.toIso8601String()
    )
}