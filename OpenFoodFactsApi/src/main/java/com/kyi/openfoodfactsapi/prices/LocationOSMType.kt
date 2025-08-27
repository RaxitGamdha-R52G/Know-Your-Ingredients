package com.kyi.openfoodfactsapi.prices

import com.kyi.openfoodfactsapi.sources.OffTagged

enum class LocationOSMType(override val offTag: String) : OffTagged {
    NODE("NODE"),
    WAY("WAY"),
    RELATION("RELATION");

    companion object {

        fun fromOffTag(offTag: String?): LocationOSMType? = entries.firstOrNull { it.offTag == offTag }
    }
}