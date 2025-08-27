package com.kyi.openfoodfactsapi.prices

import com.kyi.openfoodfactsapi.sources.OffTagged

enum class PriceType(override val offTag: String) : OffTagged {
    PRODUCT("PRODUCT"),
    CATEGORY("CATEGORY");

    companion object {

        fun fromOffTag(offTag: String?): PriceType? = entries.firstOrNull { it.offTag == offTag }
    }
}