package com.kyi.openfoodfactsapi.models

import com.kyi.openfoodfactsapi.sources.JsonObject

class OrderedNutrient(
    val id: String,
    val name: String? = null,
    val important: Boolean,
    val displayInEditForm: Boolean,
    val subNutrients: List<OrderedNutrient>? = null
) : JsonObject() {

    override fun toJson(): Map<String, Any?> = mapOf(
        "id" to id,
        "name" to name,
        "important" to important,
        "display_in_edit_form" to displayInEditForm,
        "nutrients" to subNutrients
    )

    val nutrient: Nutrient? get() = Nutrient.fromOffTag(id)

    companion object {

        fun fromJson(json: Map<String, Any?>): OrderedNutrient = OrderedNutrient(
            id = json["id"] as String,
            name = json["name"] as String?,
            important = json["important"] as Boolean,
            displayInEditForm = json["display_in_edit_form"] as Boolean,
            subNutrients = (json["nutrients"] as List<*>?)?.map { fromJson(it as Map<String, Any?>) }
        )
    }
}