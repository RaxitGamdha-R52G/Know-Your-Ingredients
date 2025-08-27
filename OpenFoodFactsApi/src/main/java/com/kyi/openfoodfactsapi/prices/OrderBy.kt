package com.kyi.openfoodfactsapi.prices

import com.kyi.openfoodfactsapi.sources.OffTagged

interface OrderByField : OffTagged

class OrderBy<T : OrderByField>(val field: T, val ascending: Boolean) : OffTagged {

    override val offTag: String get() = "${if (ascending) "" else "-"}${field.offTag}"
}