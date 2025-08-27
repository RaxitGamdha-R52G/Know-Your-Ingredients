package com.kyi.openfoodfactsapi.models

import com.kyi.openfoodfactsapi.sources.OffTagged

enum class VegetarianStatus(override val offTag: String) : OffTagged {
    VEGETARIAN("en:vegetarian"),
    NON_VEGETARIAN("en:non-vegetarian"),
    MAYBE_VEGETARIAN("en:maybe-vegetarian"),
    VEGETARIAN_STATUS_UNKNOWN("en:vegetarian-status-unknown");
}