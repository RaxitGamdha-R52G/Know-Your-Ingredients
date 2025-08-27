package com.kyi.openfoodfactsapi.prices

import com.kyi.openfoodfactsapi.sources.JsonObject

class PriceUser(val json: Map<String, Any?>) : JsonObject() {

    companion object {

        fun fromJson(json: Map<String, Any?>): PriceUser = PriceUser(json)
    }

    val userId: String get() = json["user_id"] as String

    val priceCount: Int? get() = getInt("price_count")

    val locationCount: Int? get() = getInt("location_count")

    val productCount: Int? get() = getInt("product_count")

    val proofCount: Int? get() = getInt("proof_count")

    val priceCurrencyCount: Int? get() = getInt("price_currency_count")

    val priceKindCommunityCount: Int? get() = getInt("price_kind_community_count")

    val priceKindConsumptionCount: Int? get() = getInt("price_kind_consumption_count")

    val proofKindCommunityCount: Int? get() = getInt("proof_kind_community_count")

    val proofKindConsumptionCount: Int? get() = getInt("proof_kind_consumption_count")

    val priceTypeProductCount: Int? get() = getInt("price_type_product_count")

    val priceTypeCategoryCount: Int? get() = getInt("price_type_category_count")

    val priceInProofOwnedCount: Int? get() = getInt("price_in_proof_owned_count")

    val priceInProofNotOwnedCount: Int? get() = getInt("price_in_proof_not_owned_count")

    val priceNotOwnedInProofOwnedCount: Int? get() = getInt("price_not_owned_in_proof_owned_count")

    val locationTypeOsmCountryCount: Int? get() = getInt("location_type_osm_country_count")

    private fun getInt(key: String): Int? = if (json.containsKey(key)) json[key] as Int? else null

    override fun toJson(): Map<String, Any?> = json
}