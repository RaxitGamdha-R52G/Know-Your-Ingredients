package com.kyi.openfoodfactsapi.prices

import com.kyi.openfoodfactsapi.sources.OffTagged

enum class Flavor(override val offTag: String) : OffTagged {
    OPEN_FOOD_FACTS("off"),
    OPEN_BEAUTY_FACTS("obf"),
    OPEN_PET_FOOD_FACTS("opff"),
    OPEN_PRODUCT_FACTS("opf"),
    OPEN_FOOD_PRODUCT_FACTS_PRO("off-pro");
}