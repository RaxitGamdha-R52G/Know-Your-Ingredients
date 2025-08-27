package com.kyi.openfoodfactsapi.prices

import com.kyi.openfoodfactsapi.sources.JsonObject
import com.kyi.openfoodfactsapi.utils.JsonHelper

class Price(
    var productCode: String? = null,
    var productName: String? = null,
    var categoryTag: String? = null,
    var labelsTags: List<String>? = null,
    var originsTags: List<String>? = null,
    var price: Double,
    var priceIsDiscounted: Boolean? = null,
    var priceWithoutDiscount: Double? = null,
    var discountType: DiscountType? = null,
    var pricePer: PricePer? = null,
    var currency: Currency,
    var locationOSMId: Int? = null,
    var locationOSMType: LocationOSMType? = null,
    var date: DateTime,
    var proofId: Int? = null,
    var id: Int,
    var productId: Int? = null,
    var locationId: Int? = null,
    var proof: Proof? = null,
    var location: Location? = null,
    var product: PriceProduct? = null,
    var receiptQuantity: Int? = null,
    var type: PriceType? = null,
    var owner: String,
    var created: DateTime,
    var updated: DateTime? = null
) : JsonObject() {

    companion object {

        fun fromJson(json: Map<String, Any?>): Price = Price(
            productCode = json["product_code"] as String?,
            productName = json["product_name"] as String?,
            categoryTag = json["category_tag"] as String?,
            labelsTags = (json["labels_tags"] as List<*>?)?.map { it as String },
            originsTags = (json["origins_tags"] as List<*>?)?.map { it as String },
            price = json["price"] as Double,
            priceIsDiscounted = json["price_is_discounted"] as Boolean?,
            priceWithoutDiscount = json["price_without_discount"] as Double?,
            discountType = DiscountType.fromOffTag(json["discount_type"] as String?),
            pricePer = PricePer.fromOffTag(json["price_per"] as String?),
            currency = Currency.fromName(json["currency"] as String)!!,
            locationOSMId = json["location_osm_id"] as Int?,
            locationOSMType = LocationOSMType.fromOffTag(json["location_osm_type"] as String?),
            date = JsonHelper.stringTimestampToDate(json["date"] as String),
            proofId = json["proof_id"] as Int?,
            id = json["id"] as Int,
            productId = json["product_id"] as Int?,
            locationId = json["location_id"] as Int?,
            proof = json["proof"]?.let { Proof.fromJson(it as Map<String, Any?>) },
            location = json["location"]?.let { Location.fromJson(it as Map<String, Any?>) },
            product = json["product"]?.let { PriceProduct.fromJson(it as Map<String, Any?>) },
            receiptQuantity = json["receipt_quantity"] as Int?,
            type = PriceType.fromOffTag(json["type"] as String?),
            owner = json["owner"] as String,
            created = JsonHelper.stringTimestampToDate(json["created"] as String),
            updated = JsonHelper.nullableStringTimestampToDate(json["updated"] as String?)
        )
    }

    override fun toJson(): Map<String, Any?> = mapOf(
        "product_code" to productCode,
        "product_name" to productName,
        "category_tag" to categoryTag,
        "labels_tags" to labelsTags,
        "origins_tags" to originsTags,
        "price" to price,
        "price_is_discounted" to priceIsDiscounted,
        "price_without_discount" to priceWithoutDiscount,
        "discount_type" to discountType?.offTag,
        "price_per" to pricePer?.offTag,
        "currency" to currency.name,
        "location_osm_id" to locationOSMId,
        "location_osm_type" to locationOSMType?.offTag,
        "date" to date.toIso8601String(),
        "proof_id" to proofId,
        "id" to id,
        "product_id" to productId,
        "location_id" to locationId,
        "proof" to proof,
        "location" to location,
        "product" to product,
        "receipt_quantity" to receiptQuantity,
        "type" to type?.offTag,
        "owner" to owner,
        "created" to created.toIso8601String(),
        "updated" to updated?.toIso8601String()
    )
}