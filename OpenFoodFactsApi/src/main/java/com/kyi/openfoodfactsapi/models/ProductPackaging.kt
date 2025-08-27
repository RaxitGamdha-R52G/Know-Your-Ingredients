package com.kyi.openfoodfactsapi.models

import com.kyi.openfoodfactsapi.sources.JsonObject

class ProductPackaging : JsonObject() {

    var shape: LocalizedTag? = null

    var material: LocalizedTag? = null

    var recycling: LocalizedTag? = null

    var numberOfUnits: Int? = null

    var quantityPerUnit: String? = null

    var weightMeasured: Double? = null

    companion object {

        fun fromJson(json: Any?): ProductPackaging = ProductPackaging().apply {
            val map = json as Map<String, Any?>
            shape = LocalizedTag.fromJson(map["shape"] as Map<String, Any?>?)
            material = LocalizedTag.fromJson(map["material"] as Map<String, Any?>?)
            recycling = LocalizedTag.fromJson(map["recycling"] as Map<String, Any?>?)
            numberOfUnits = parseInt(map["number_of_units"])
            quantityPerUnit = map["quantity_per_unit"] as String?
            weightMeasured = parseDouble(map["weight_measured"])
        }
    }

    override fun toJson(): Map<String, Any?> = mapOfNotNull(
        "shape" to shape,
        "material" to material,
        "recycling" to recycling,
        "number_of_units" to numberOfUnits,
        "quantity_per_unit" to quantityPerUnit,
        "weight_measured" to weightMeasured
    )

    fun toServerData(): Map<String, String> = toDataStatic(toJson())

    override fun toString(): String = toServerData().toString()
}