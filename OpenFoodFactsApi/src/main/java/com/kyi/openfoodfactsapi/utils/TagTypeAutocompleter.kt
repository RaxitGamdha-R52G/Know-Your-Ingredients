package com.kyi.openfoodfactsapi.utils

import com.kyi.openfoodfactsapi.OpenFoodAPIClient
import com.kyi.openfoodfactsapi.models.User

class TagTypeAutocompleter(
    val tagType: TagType,
    val language: OpenFoodFactsLanguage,
    val country: OpenFoodFactsCountry? = null,
    val categories: String? = null,
    val shape: String? = null,
    val limit: Int = 25,
    val uriHelper: UriProductHelper = uriHelperFoodProd,
    val user: User? = null
) : Autocompleter {

    override suspend fun getSuggestions(input: String): List<String> =
        OpenFoodAPIClient.getSuggestions(
            tagType,
            input = input,
            language = language,
            country = country,
            categories = categories,
            shape = shape,
            limit = limit,
            uriHelper = uriHelper,
            user = user
        )
}