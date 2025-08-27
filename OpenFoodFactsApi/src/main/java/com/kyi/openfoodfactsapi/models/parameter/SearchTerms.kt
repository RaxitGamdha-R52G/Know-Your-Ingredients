package com.kyi.openfoodfactsapi.models.parameter

import com.kyi.openfoodfactsapi.sources.Parameter

/**
 * Term list search API parameter
 */
class SearchTerms(val terms: List<String>) : Parameter() {

    override fun getName(): String = "search_terms"

    override fun getValue(): String = terms.joinToString("+")
}