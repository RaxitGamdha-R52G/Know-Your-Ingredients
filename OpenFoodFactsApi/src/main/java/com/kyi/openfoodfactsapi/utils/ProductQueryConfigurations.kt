package com.kyi.openfoodfactsapi.utils

import Response
import com.kyi.openfoodfactsapi.models.ProductTypeFilter
import com.kyi.openfoodfactsapi.models.User

class ProductQueryVersion(val version: Int) {

    companion object {

        val v3 = ProductQueryVersion(3)
    }

    fun getPath(barcode: String): String = "/api/v$version/product/$barcode/"

    fun matchesV3(): Boolean = version >= 3
}

class ProductQueryConfiguration(
    val barcode: String,
    val version: ProductQueryVersion,
    language: OpenFoodFactsLanguage? = null,
    languages: List<OpenFoodFactsLanguage>? = null,
    country: OpenFoodFactsCountry? = null,
    fields: List<ProductField>? = null,
    val productTypeFilter: ProductTypeFilter? = null
) : AbstractQueryConfiguration(language = language, languages = languages, country = country, fields = fields) {

    override fun getParametersMap(): Map<String, String> {
        val result = super.getParametersMap()
        productTypeFilter?.let { result["product_type"] = it.offTag }
        return result
    }

    fun matchesV3(): Boolean = version.matchesV3()

    override fun getUriPath(): String = version.getPath(barcode)

    override suspend fun getResponse(user: User?, uriHelper: UriProductHelper): Response {
        return if (matchesV3()) {
            HttpHelper.get.doGetRequest(
                uriHelper.getUri(path = getUriPath(), queryParameters = getParametersMap()),
                user = user,
                uriHelper = uriHelper,
                addCookiesToHeader = true
            )
        } else {
            HttpHelper.get.doPostRequest(
                uriHelper.getPostUri(path = getUriPath()),
                getParametersMap(),
                user,
                uriHelper = uriHelper,
                addCredentialsToBody = false
            )
        }
    }
}