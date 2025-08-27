package com.kyi.openfoodfactsapi

import OpenFoodFactsLanguage
import com.kyi.openfoodfactsapi.models.User
import com.kyi.openfoodfactsapi.search.AutocompleteSearchResult
import com.kyi.openfoodfactsapi.search.Fuzziness
import com.kyi.openfoodfactsapi.search.TaxonomyName
import com.kyi.openfoodfactsapi.utils.HttpHelper
import com.kyi.openfoodfactsapi.utils.UriProductHelper
import com.kyi.openfoodfactsapi.utils.uriHelperFoodProd

object OpenFoodSearchAPIClient {

    private const val SUBDOMAIN = "search"

    private fun getHost(uriHelper: UriProductHelper): String = uriHelper.getHost(SUBDOMAIN)

    suspend fun autocomplete(
        query: String,
        taxonomyNames: List<TaxonomyName>,
        language: OpenFoodFactsLanguage,
        user: User? = null,
        size: Int = 10,
        fuzziness: Fuzziness = Fuzziness.none,
        uriHelper: UriProductHelper = uriHelperFoodProd
    ): AutocompleteSearchResult {
        val taxonomyTags = taxonomyNames.map { it.offTag }
        if (taxonomyTags.isEmpty()) throw Exception("Taxonomies cannot be empty!")
        val uri = uriHelper.getUri(
            path = "/autocomplete",
            queryParameters = mapOf(
                "q" to query,
                "taxonomy_names" to taxonomyTags.joinToString(","),
                "lang" to language.offTag,
                "size" to size.toString(),
                "fuzziness" to fuzziness.offTag
            )
        )
        val response = HttpHelper.get.doGetRequest(uri, user = user, uriHelper = uriHelper)
        return AutocompleteSearchResult.fromJson(HttpHelper.get.jsonDecodeUtf8(response))
    }
}