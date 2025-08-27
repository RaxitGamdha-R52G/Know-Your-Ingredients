package com.kyi.openfoodfactsapi.sources

interface OffTagged {

    val offTag: String

    companion object {

        fun fromOffTag(offTag: String?, values: Iterable<OffTagged>): OffTagged? {
            if (offTag == null) return null
            for (tagged in values) {
                if (tagged.offTag == offTag) return tagged
            }
            return null
        }
    }
}