package com.kyi.openfoodfactsapi.utils

import com.kyi.openfoodfactsapi.sources.JsonObject
import com.kyi.openfoodfactsapi.sources.Parameter


abstract class TaxonomyQueryConfiguration<T : JsonObject, F : Enum<*>>(
    val tagType: TagType,
    val tags: List<String>,
    val languages: List<OpenFoodFactsLanguage> = OpenFoodAPIConfiguration.globalLanguages ?: emptyList(),
    val country: OpenFoodFactsCountry? = null,
    val includeChildren: Boolean = false,
    val fields: List<F> = emptyList(),
    val additionalParameters: List<Parameter> = emptyList()
) {

    private val isRootConfiguration: Boolean = tags.isEmpty()

    fun getParametersMap(): Map<String, String> {
        val result = mutableMapOf<String, String>()

        result["tagtype"] = tagType.offTag
        if (isRootConfiguration) {
            result["include_root_entries"] = "1"
        } else {
            if (tags.isNotEmpty()) {
                result["tags"] = tags.joinToString(",")
            }
        }
        result["include_children"] = if (includeChildren) "1" else "0"

        if (languages.isNotEmpty()) {
            result["lc"] = languages.joinToString(",") { it.offTag }
        }

        result["cc"] = OpenFoodAPIConfiguration.computeCountryCode(country, null)!!

        if (fields.isNotEmpty()) {
            val fieldsStrings = convertFieldsToStrings(fields)
            result["fields"] = fieldsStrings.joinToString(",")
        }

        for (parameter in additionalParameters) {
            result[parameter.getName()] = parameter.getValue()
        }
        return result
    }

    fun getPostUri(uriHelper: UriProductHelper): Uri = uriHelper.getPostUri(path = "api/v2/taxonomy")

    abstract val ignoredFields: Set<F>

    abstract fun convertResults(jsonData: Any?): Map<String, T>

    abstract fun convertFieldsToStrings(fields: Iterable<F>): Iterable<String>
}