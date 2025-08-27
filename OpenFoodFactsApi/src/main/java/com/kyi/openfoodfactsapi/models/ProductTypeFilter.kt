package com.kyi.openfoodfactsapi.models

import com.kyi.openfoodfactsapi.sources.OffTagged


class ProductTypeFilter(override val offTag: String) : OffTagged {

    constructor(productType: ProductType) : this(productType.offTag)

    companion object {
        val ALL = ProductTypeFilter("all")
    }
}