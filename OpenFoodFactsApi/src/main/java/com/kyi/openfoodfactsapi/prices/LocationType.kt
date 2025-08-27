package com.kyi.openfoodfactsapi.prices

import com.kyi.openfoodfactsapi.sources.OffTagged

enum class LocationType(override val offTag: String) : OffTagged {
    OSM("OSM"),
    ONLINE("ONLINE");

    companion object {

        fun fromOffTag(offTag: String?): LocationType? = entries.firstOrNull { it.offTag == offTag }
    }
}