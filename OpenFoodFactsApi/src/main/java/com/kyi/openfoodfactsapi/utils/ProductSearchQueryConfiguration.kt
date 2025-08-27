package com.kyi.openfoodfactsapi.utils

import com.kyi.openfoodfactsapi.sources.Parameter

class ProductSearchQueryConfiguration(
    language: OpenFoodFactsLanguage? = null,
    languages: List<OpenFoodFactsLanguage>? = null,
    country: OpenFoodFactsCountry? = null,
    fields: List<ProductField>? = null,
    val parametersList: List<Parameter>,
    val version: ProductQueryVersion
) : AbstractQueryConfiguration(language = language, languages = languages, country = country, fields = fields, additionalParameters = parametersList) {

    fun getFieldsKeys(): List<String> {
        val result = mutableListOf<String>()
        fields?.forEach { result.add(it.offTag) }
        return result
    }

    override fun getParametersMap(): Map<String, String> {
        val result = super.getParametersMap()
        result["search_terms"] = ""
        result["json"] = "1"
        result["api_version"] = "${version.version}"
        return result
    }

    override fun getUriPath(): String = "/cgi/search.pl"
}