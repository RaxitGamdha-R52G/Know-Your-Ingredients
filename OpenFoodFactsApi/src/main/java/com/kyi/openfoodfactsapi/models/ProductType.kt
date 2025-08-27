package com.kyi.openfoodfactsapi.models

import com.kyi.openfoodfactsapi.sources.OffTagged


enum class ProductType(override val offTag: String) : OffTagged {
    FOOD("food"),
    BEAUTY("beauty"),
    PET_FOOD("petfood"),
    PRODUCT("product");

    companion object {

        fun fromOffTag(offTag: String?): ProductType? = entries.firstOrNull { it.offTag == offTag }
    }
}