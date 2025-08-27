package com.kyi.openfoodfactsapi.utils

import com.kyi.openfoodfactsapi.sources.OffTagged

enum class ServerType(override val offTag: String) : OffTagged {
    OPEN_FOOD_FACTS("off"),
    OPEN_BEAUTY_FACTS("obf"),
    OPEN_PET_FOOD_FACTS("opff"),
    OPEN_PRODUCT_FACTS("opf");

    companion object {

        fun fromOffTag(offTag: String?): ServerType? = entries.firstOrNull { it.offTag == offTag }
    }
}