package com.kyi.openfoodfactsapi.search

import OpenFoodFactsLanguage
import com.kyi.openfoodfactsapi.OpenFoodAPIClient
import com.kyi.openfoodfactsapi.models.User
import com.kyi.openfoodfactsapi.utils.Autocompleter
import com.kyi.openfoodfactsapi.utils.UriProductHelper
import com.kyi.openfoodfactsapi.utils.uriHelperFoodProd

class TaxonomyNameAutocompleter(
    val taxonomyNames: List<TaxonomyName>,
    val language: OpenFoodFactsLanguage,
    val limit: Int = 25,
    val uriHelper: UriProductHelper = uriHelperFoodProd,
    val user: User? = null,
    val fuzziness: Fuzziness = Fuzziness.NONE
) : Autocompleter {

    override suspend fun getSuggestions(input: String): List<String> {
        val results = OpenFoodAPIClient.autocomplete(
            language = language,
            query = input,
            taxonomyNames = taxonomyNames,
            size = limit,
            user = user,
            uriHelper = uriHelper,
            fuzziness = fuzziness
        )
        val result = mutableListOf<String>()
        results.options?.forEach { item ->
            result.add(item.text)
        }
        return result
    }
}