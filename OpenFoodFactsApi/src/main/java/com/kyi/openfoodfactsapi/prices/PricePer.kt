package com.kyi.openfoodfactsapi.prices

import com.kyi.openfoodfactsapi.sources.OffTagged

enum class PricePer(override val offTag: String) : OffTagged {
    UNIT("UNIT"),
    KILOGRAM("KILOGRAM");

    companion object {

        fun fromOffTag(offTag: String?): PricePer? = entries.firstOrNull { it.offTag == offTag }
    }
}