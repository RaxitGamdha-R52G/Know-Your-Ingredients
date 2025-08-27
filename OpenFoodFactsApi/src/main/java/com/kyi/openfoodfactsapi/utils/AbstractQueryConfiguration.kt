package com.kyi.openfoodfactsapi.utils

import http.Response
import com.kyi.openfoodfactsapi.models.User
import com.kyi.openfoodfactsapi.models.parameter.TagFilter
import com.kyi.openfoodfactsapi.sources.Parameter

abstract class AbstractQueryConfiguration(
    var language: OpenFoodFactsLanguage? = null,
    var languages: List<OpenFoodFactsLanguage>? = null,
    val country: OpenFoodFactsCountry? = null,
    var fields: List<ProductField>? = null,
    val additionalParameters: List<Parameter> = emptyList()
) {

    init {
        fields = fields ?: listOf(ProductField.ALL)
        if (languages != null && language != null && languages!!.isNotEmpty()) {
            throw IllegalArgumentException("[languages] cannot be used together with [language]")
        }
    }

    open fun getParametersMap(): Map<String, String> {
        val result = mutableMapOf<String, String>()

        val queryLanguages = when {
            language != null -> listOf(language!!)
            languages != null -> languages!!
            OpenFoodAPIConfiguration.globalLanguages != null -> OpenFoodAPIConfiguration.globalLanguages!!
            else -> emptyList()
        }

        if (queryLanguages.isNotEmpty()) {
            result["lc"] = queryLanguages.joinToString(",") { it.offTag }
            result["tags_lc"] = queryLanguages.first().offTag
        }

        val countryCode = computeCountryCode()
        countryCode?.let { result["cc"] = it }

        fields?.let { nonNullFields ->
            val ignoreFieldsFilter = nonNullFields.any { it == ProductField.ALL }
            if (!ignoreFieldsFilter) {
                val fieldsStrings = convertFieldsToStrings(nonNullFields, queryLanguages)
                result["fields"] = fieldsStrings.joinToString(",")
            }
        }

        var filterTagCount = 0
        for (p in additionalParameters) {
            if (p is TagFilter) {
                result["tagtype_$filterTagCount"] = p.getTagType()
                result["tag_contains_$filterTagCount"] = p.getContains()
                result["tag_$filterTagCount"] = p.getTagName()
                filterTagCount++
            } else {
                result[p.getName()] = p.getValue()
            }
        }

        return result
    }

    protected fun computeCountryCode(): String? = OpenFoodAPIConfiguration.computeCountryCode(country, null)

    protected abstract fun getUriPath(): String

    open suspend fun getResponse(user: User?, uriHelper: UriProductHelper): Response =
        HttpHelper.get.doPostRequest(
            uriHelper.getPostUri(path = getUriPath()),
            getParametersMap(),
            user,
            uriHelper = uriHelper,
            addCredentialsToBody = false
        )
}