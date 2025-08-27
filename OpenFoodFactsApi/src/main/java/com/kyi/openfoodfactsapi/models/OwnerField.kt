package com.kyi.openfoodfactsapi.models

import ProductField
import OpenFoodFactsLanguage
import com.kyi.openfoodfactsapi.sources.OffTagged

class OwnerField(override val offTag: String) : OffTagged {

    constructor(nutrient: Nutrient) : this(nutrient.offTag)

    constructor(productField: ProductField, language: OpenFoodFactsLanguage) {
        val inLanguages = productField.inLanguages
        offTag = if (inLanguages == null) {
            productField.offTag
        } else {
            "${inLanguages.offTag}${language.offTag}"
        }
    }
}