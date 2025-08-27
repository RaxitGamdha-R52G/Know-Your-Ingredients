package com.kyi.openfoodfactsapi.folksonomy

import Response
import com.kyi.openfoodfactsapi.models.KeyStats
import com.kyi.openfoodfactsapi.models.ProductList
import com.kyi.openfoodfactsapi.models.ProductStats
import com.kyi.openfoodfactsapi.models.ProductTag
import com.kyi.openfoodfactsapi.models.ValueCount
import com.kyi.openfoodfactsapi.prices.MaybeError
import com.kyi.openfoodfactsapi.utils.HttpHelper
import com.kyi.openfoodfactsapi.utils.UriHelper
import com.kyi.openfoodfactsapi.utils.uriHelperFolksonomyProd

object FolksonomyAPIClient {

    suspend fun hello(uriHelper: UriHelper = uriHelperFolksonomyProd) {
        val response = HttpHelper.get.doGetRequest(
            uriHelper.getUri(path = "/"),
            uriHelper = uriHelper
        )
        checkResponse(response)
    }

    suspend fun getAuthenticationToken(
        username: String,
        password: String,
        uriHelper: UriHelper = uriHelperFolksonomyProd
    ): MaybeError<String> {
        val uri = uriHelper.getUri(path = "/auth")
        val response = http.post(uri, body = mapOf("username" to username, "password" to password))
        if (response.statusCode == 200) {
            try {
                val decodedResponse = HttpHelper.get.jsonDecodeUtf8(response)
                return MaybeError.value(decodedResponse["access_token"] as String)
            } catch (e: Exception) {
                //
            }
        }
        return MaybeError.responseError(response)
    }

    suspend fun getProductStats(
        key: String? = null,
        value: String? = null,
        owner: String? = null,
        uriHelper: UriHelper = uriHelperFolksonomyProd
    ): List<ProductStats> {
        val parameters = buildJson(owner = owner, key = key, value = value)
        val response = HttpHelper.get.doGetRequest(
            uriHelper.getUri(path = "products/stats", queryParameters = parameters),
            uriHelper = uriHelper
        )
        checkResponse(response)
        val json = HttpHelper.get.jsonDecode(response.body) as List<*>
        return json.map { ProductStats.fromJson(it as Map<String, Any?>) }
    }

    suspend fun getProducts(
        key: String,
        value: String? = null,
        owner: String? = null,
        uriHelper: UriHelper = uriHelperFolksonomyProd
    ): Map<String, String> {
        val parameters = buildJson(key = key, value = value, owner = owner)
        val response = HttpHelper.get.doGetRequest(
            uriHelper.getUri(path = "products", queryParameters = parameters),
            uriHelper = uriHelper
        )
        checkResponse(response)
        val json = HttpHelper.get.jsonDecode(response.body) as List<*>
        val result = mutableMapOf<String, String>()
        for (element in json) {
            val productList = ProductList.fromJson(element as Map<String, Any?>)
            if (productList.key != key) throw Exception("Unexpected key: ${productList.key}")
            result[productList.barcode] = productList.value
        }
        return result
    }

    suspend fun getProductTags(
        barcode: String,
        owner: String? = null,
        uriHelper: UriHelper = uriHelperFolksonomyProd
    ): Map<String, ProductTag> {
        val parameters = mutableMapOf<String, String>()
        owner?.let { parameters["owner"] = it }
        val response = HttpHelper.get.doGetRequest(
            uriHelper.getUri(path = "product/$barcode", queryParameters = parameters),
            uriHelper = uriHelper
        )
        checkResponse(response)
        val result = mutableMapOf<String, ProductTag>()
        if (response.body == "null") return result
        val json = HttpHelper.get.jsonDecode(response.body) as List<*>
        for (element in json) {
            val productTag = ProductTag.fromJson(element as Map<String, Any?>)
            result[productTag.key] = productTag
        }
        return result
    }

    suspend fun getProductTag(
        barcode: String,
        key: String,
        owner: String? = null,
        uriHelper: UriHelper = uriHelperFolksonomyProd
    ): ProductTag? {
        val parameters = mutableMapOf<String, String>()
        owner?.let { parameters["owner"] = it }
        val response = HttpHelper.get.doGetRequest(
            uriHelper.getUri(path = "product/$barcode/$key", queryParameters = parameters),
            uriHelper = uriHelper
        )
        checkResponse(response)
        if (response.body == "null") return null
        val json = HttpHelper.get.jsonDecode(response.body) as Map<String, Any?>
        return ProductTag.fromJson(json)
    }

    suspend fun deleteProductTag(
        barcode: String,
        key: String,
        version: Int,
        owner: String? = null,
        bearerToken: String,
        uriHelper: UriHelper = uriHelperFolksonomyProd
    ): MaybeError<Boolean> {
        val response = HttpHelper.get.doDeleteRequest(
            uriHelper.getUri(
                path = "product/$barcode/$key",
                queryParameters = mapOf("version" to "$version", "owner" to owner.orEmpty())
            ),
            uriHelper = uriHelper,
            bearerToken = bearerToken
        )
        if (response.statusCode == 200) {
            return MaybeError.value(true)
        }
        return MaybeError.responseError(response)
    }

    suspend fun getProductTagVersions(
        barcode: String,
        key: String,
        owner: String? = null,
        uriHelper: UriHelper = uriHelperFolksonomyProd
    ): List<ProductTag> {
        val parameters = mutableMapOf<String, String>()
        owner?.let { parameters["owner"] = it }
        val response = HttpHelper.get.doGetRequest(
            uriHelper.getUri(path = "product/$barcode/$key/versions", queryParameters = parameters),
            uriHelper = uriHelper
        )
        checkResponse(response)
        val result = mutableListOf<ProductTag>()
        if (response.body == "null") return result
        val json = HttpHelper.get.jsonDecode(response.body) as List<*>
        for (element in json) {
            result.add(ProductTag.fromJson(element as Map<String, Any?>))
        }
        return result
    }

    suspend fun updateProductTag(
        barcode: String,
        key: String,
        value: String,
        version: Int,
        ownerIfPrivate: String? = null,
        bearerToken: String,
        uriHelper: UriHelper = uriHelperFolksonomyProd
    ): MaybeError<Boolean> {
        val body = buildJson(barcode = barcode, key = key, value = value, version = version)
        val response = HttpHelper.get.doPutRequest(
            uriHelper.getUri(
                path = "product",
                queryParameters = mapOf(
                    "version" to "$version",
                    "owner" to ownerIfPrivate.orEmpty()
                )
            ),
            jsonEncode(body),
            uriHelper = uriHelper,
            bearerToken = bearerToken
        )
        if (response.statusCode == 200) {
            return MaybeError.value(true)
        }
        return MaybeError.responseError(response)
    }

    suspend fun addProductTag(
        barcode: String,
        key: String,
        value: String,
        ownerIfPrivate: String? = null,
        bearerToken: String,
        uriHelper: UriHelper = uriHelperFolksonomyProd
    ): MaybeError<Boolean> {
        val body = buildJson(barcode = barcode, key = key, value = value, version = null)
        val response = HttpHelper.get.doPostJsonRequest(
            uriHelper.getUri(path = "/product"),
            jsonEncode(body),
            uriHelper = uriHelper,
            bearerToken = bearerToken
        )
        if (response.statusCode == 200) {
            return MaybeError.value(true)
        }
        return MaybeError.responseError(response)
    }

    suspend fun getKeys(
        owner: String? = null,
        query: String? = null,
        uriHelper: UriHelper = uriHelperFolksonomyProd
    ): Map<String, KeyStats> {
        val parameters = mutableMapOf<String, String>()
        owner?.let { parameters["owner"] = it }
        query?.let { parameters["q"] = it }
        val response = HttpHelper.get.doGetRequest(
            uriHelper.getUri(path = "keys", queryParameters = parameters),
            uriHelper = uriHelper
        )
        checkResponse(response)
        val result = mutableMapOf<String, KeyStats>()
        val json = HttpHelper.get.jsonDecode(response.body) as List<*>
        for (element in json) {
            val item = KeyStats.fromJson(element as Map<String, Any?>)
            result[item.key] = item
        }
        return result
    }

    suspend fun getValues(
        key: String,
        owner: String? = null,
        query: String? = null,
        limit: Int? = null,
        uriHelper: UriHelper = uriHelperFolksonomyProd
    ): Map<String, ValueCount> {
        val parameters = mutableMapOf<String, String>()
        owner?.let { parameters["owner"] = it }
        query?.let { parameters["q"] = it }
        limit?.let { parameters["limit"] = it.toString() }
        val response = HttpHelper.get.doGetRequest(
            uriHelper.getUri(path = "values/$key", queryParameters = parameters),
            uriHelper = uriHelper
        )
        checkResponse(response)
        val result = mutableMapOf<String, ValueCount>()
        val json = HttpHelper.get.jsonDecode(response.body) as List<*>
        for (element in json) {
            val item = ValueCount.fromJson(element as Map<String, Any?>)
            result[item.value] = item
        }
        return result
    }

    suspend fun ping(uriHelper: UriHelper = uriHelperFolksonomyProd) {
        val response = HttpHelper.get.doGetRequest(
            uriHelper.getUri(path = "ping"),
            uriHelper = uriHelper
        )
        checkResponse(response)
    }

    private fun checkResponse(response: Response, authorizedStatus: List<Int> = listOf(200)) {
        if (!authorizedStatus.contains(response.statusCode)) {
            throw Exception("Wrong status code: ${response.statusCode}")
        }
    }

    private fun buildJson(
        barcode: String? = null,
        key: String? = null,
        value: String? = null,
        version: Int? = null,
        owner: String? = null
    ): Map<String, Any> = mapOfNotNull(
        "product" to barcode,
        "owner" to owner,
        "k" to key,
        "v" to value,
        "version" to version
    )
}