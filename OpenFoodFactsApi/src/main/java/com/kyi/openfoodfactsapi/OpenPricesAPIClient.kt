package com.kyi.openfoodfactsapi

import android.net.Uri
import com.kyi.openfoodfactsapi.prices.CreateProofParameters
import com.kyi.openfoodfactsapi.prices.Currency
import com.kyi.openfoodfactsapi.prices.GetLocationsParameters
import com.kyi.openfoodfactsapi.prices.GetLocationsResult
import com.kyi.openfoodfactsapi.prices.GetParametersHelper
import com.kyi.openfoodfactsapi.prices.GetPriceProductsParameters
import com.kyi.openfoodfactsapi.prices.GetPriceProductsResult
import com.kyi.openfoodfactsapi.prices.GetPricesParameters
import com.kyi.openfoodfactsapi.prices.GetPricesResult
import com.kyi.openfoodfactsapi.prices.GetProofsParameters
import com.kyi.openfoodfactsapi.prices.GetProofsResult
import com.kyi.openfoodfactsapi.prices.GetUsersParameters
import com.kyi.openfoodfactsapi.prices.GetUsersResult
import com.kyi.openfoodfactsapi.prices.Location
import com.kyi.openfoodfactsapi.prices.LocationOSMType
import com.kyi.openfoodfactsapi.prices.MaybeError
import com.kyi.openfoodfactsapi.prices.Price
import com.kyi.openfoodfactsapi.prices.PriceProduct
import com.kyi.openfoodfactsapi.prices.PriceTotalStats
import com.kyi.openfoodfactsapi.prices.PriceUser
import com.kyi.openfoodfactsapi.prices.Proof
import com.kyi.openfoodfactsapi.prices.ProofType
import com.kyi.openfoodfactsapi.prices.Session
import com.kyi.openfoodfactsapi.prices.UpdatePriceParameters
import com.kyi.openfoodfactsapi.prices.UpdateProofParameters
import com.kyi.openfoodfactsapi.utils.HttpHelper
import com.kyi.openfoodfactsapi.utils.UriProductHelper
import com.kyi.openfoodfactsapi.utils.UriReader
import com.kyi.openfoodfactsapi.utils.uriHelperFoodProd
import http.MultipartFile
import http.MultipartRequest
import http_parser.MediaType
import path.basename

object OpenPricesAPIClient {

    const val STATUS_RUNNING = "running"

    private const val SUBDOMAIN = "prices"

    private fun getHost(uriHelper: UriProductHelper): String = uriHelper.getHost(SUBDOMAIN)

    fun getUri(
        path: String,
        queryParameters: Map<String, Any?>? = null,
        uriHelper: UriProductHelper = uriHelperFoodProd,
        addUserAgentParameters: Boolean? = null
    ): Uri = uriHelper.getUri(
        path = path,
        queryParameters = queryParameters,
        forcedHost = getHost(uriHelper),
        addUserAgentParameters = addUserAgentParameters
    )

    suspend fun getUser(
        username: String,
        uriHelper: UriProductHelper = uriHelperFoodProd
    ): MaybeError<PriceUser> {
        val uri = getUri(path = "/api/v1/users/$username")
        val response = HttpHelper.get.doGetRequest(uri, uriHelper = uriHelper)
        if (response.statusCode == 200) {
            try {
                val decodedResponse = HttpHelper.get.jsonDecodeUtf8(response)
                return MaybeError.value(PriceUser.fromJson(decodedResponse))
            } catch (e: Exception) {
                //
            }
        }
        return MaybeError.responseError(response)
    }

    suspend fun getPrices(
        parameters: GetPricesParameters,
        uriHelper: UriProductHelper = uriHelperFoodProd,
        bearerToken: String? = null
    ): MaybeError<GetPricesResult> {
        val uri = getUri(
            path = "/api/v1/prices",
            queryParameters = parameters.getQueryParameters(),
            uriHelper = uriHelper
        )
        val response =
            HttpHelper.get.doGetRequest(uri, uriHelper = uriHelper, bearerToken = bearerToken)
        if (response.statusCode == 200) {
            try {
                val decodedResponse = HttpHelper.get.jsonDecodeUtf8(response)
                return MaybeError.value(GetPricesResult.fromJson(decodedResponse))
            } catch (e: Exception) {
                //
            }
        }
        return MaybeError.responseError(response)
    }

    suspend fun getPrice(
        priceId: Int,
        uriHelper: UriProductHelper = uriHelperFoodProd
    ): MaybeError<Price> {
        val uri = getUri(path = "/api/v1/prices/$priceId", uriHelper = uriHelper)
        val response = HttpHelper.get.doGetRequest(uri, uriHelper = uriHelper)
        if (response.statusCode == 200) {
            try {
                val decodedResponse = HttpHelper.get.jsonDecodeUtf8(response)
                return MaybeError.value(Price.fromJson(decodedResponse))
            } catch (e: Exception) {
                //
            }
        }
        return MaybeError.responseError(response)
    }

    suspend fun getOSMLocation(
        locationOSMId: Int,
        locationOSMType: LocationOSMType,
        uriHelper: UriProductHelper = uriHelperFoodProd
    ): MaybeError<Location> {
        val uri = getUri(
            path = "/api/v1/locations/osm/${locationOSMType.offTag}/$locationOSMId",
            uriHelper = uriHelper
        )
        val response = HttpHelper.get.doGetRequest(uri, uriHelper = uriHelper)
        if (response.statusCode == 200) {
            try {
                val decodedResponse = HttpHelper.get.jsonDecodeUtf8(response)
                return MaybeError.value(Location.fromJson(decodedResponse))
            } catch (e: Exception) {
                //
            }
        }
        return MaybeError.responseError(response)
    }

    suspend fun getLocations(
        parameters: GetLocationsParameters,
        uriHelper: UriProductHelper = uriHelperFoodProd,
        bearerToken: String? = null
    ): MaybeError<GetLocationsResult> {
        val uri = getUri(
            path = "/api/v1/locations",
            queryParameters = parameters.getQueryParameters(),
            uriHelper = uriHelper
        )
        val response =
            HttpHelper.get.doGetRequest(uri, uriHelper = uriHelper, bearerToken = bearerToken)
        if (response.statusCode == 200) {
            try {
                val decodedResponse = HttpHelper.get.jsonDecodeUtf8(response)
                return MaybeError.value(GetLocationsResult.fromJson(decodedResponse))
            } catch (e: Exception) {
                //
            }
        }
        return MaybeError.responseError(response)
    }

    suspend fun getLocation(
        locationId: Int,
        uriHelper: UriProductHelper = uriHelperFoodProd
    ): MaybeError<Location> {
        val uri = getUri(path = "/api/v1/locations/$locationId", uriHelper = uriHelper)
        val response = HttpHelper.get.doGetRequest(uri, uriHelper = uriHelper)
        if (response.statusCode == 200) {
            try {
                val decodedResponse = HttpHelper.get.jsonDecodeUtf8(response)
                return MaybeError.value(Location.fromJson(decodedResponse))
            } catch (e: Exception) {
                //
            }
        }
        return MaybeError.responseError(response)
    }

    suspend fun getPriceProducts(
        parameters: GetPriceProductsParameters,
        uriHelper: UriProductHelper = uriHelperFoodProd,
        bearerToken: String? = null
    ): MaybeError<GetPriceProductsResult> {
        val uri = getUri(
            path = "/api/v1/products",
            queryParameters = parameters.getQueryParameters(),
            uriHelper = uriHelper
        )
        val response =
            HttpHelper.get.doGetRequest(uri, uriHelper = uriHelper, bearerToken = bearerToken)
        if (response.statusCode == 200) {
            try {
                val decodedResponse = HttpHelper.get.jsonDecodeUtf8(response)
                return MaybeError.value(GetPriceProductsResult.fromJson(decodedResponse))
            } catch (e: Exception) {
                //
            }
        }
        return MaybeError.responseError(response)
    }

    suspend fun getPriceProductById(
        productId: Int,
        uriHelper: UriProductHelper = uriHelperFoodProd
    ): MaybeError<PriceProduct> {
        val uri = getUri(path = "/api/v1/products/$productId", uriHelper = uriHelper)
        val response = HttpHelper.get.doGetRequest(uri, uriHelper = uriHelper)
        if (response.statusCode == 200) {
            try {
                val decodedResponse = HttpHelper.get.jsonDecodeUtf8(response)
                return MaybeError.value(PriceProduct.fromJson(decodedResponse))
            } catch (e: Exception) {
                //
            }
        }
        return MaybeError.responseError(response)
    }

    suspend fun getPriceProductByCode(
        productCode: String,
        uriHelper: UriProductHelper = uriHelperFoodProd
    ): MaybeError<PriceProduct> {
        val uri = getUri(path = "/api/v1/products/code/$productCode", uriHelper = uriHelper)
        val response = HttpHelper.get.doGetRequest(uri, uriHelper = uriHelper)
        if (response.statusCode == 200) {
            try {
                val decodedResponse = HttpHelper.get.jsonDecodeUtf8(response)
                return MaybeError.value(PriceProduct.fromJson(decodedResponse))
            } catch (e: Exception) {
                //
            }
        }
        return MaybeError.responseError(response)
    }

    suspend fun getStatus(uriHelper: UriProductHelper = uriHelperFoodProd): MaybeError<String> {
        val uri = getUri(path = "/api/v1/status", uriHelper = uriHelper)
        val response = HttpHelper.get.doGetRequest(uri, uriHelper = uriHelper)
        if (response.statusCode == 200) {
            try {
                val decodedResponse = HttpHelper.get.jsonDecodeUtf8(response)
                return MaybeError.value(decodedResponse["status"] as String)
            } catch (e: Exception) {
                //
            }
        }
        return MaybeError.responseError(response)
    }

    suspend fun getAuthenticationToken(
        username: String,
        password: String,
        setCookie: Boolean = false,
        uriHelper: UriProductHelper = uriHelperFoodProd
    ): MaybeError<String> {
        val uri = getUri(
            path = "/api/v1/auth${if (setCookie) "?set_cookie=1" else ""}",
            uriHelper = uriHelper
        )
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

    suspend fun getUserSession(
        uriHelper: UriProductHelper = uriHelperFoodProd,
        bearerToken: String
    ): MaybeError<Session> {
        val uri = getUri(path = "/api/v1/session", uriHelper = uriHelper)
        val response =
            HttpHelper.get.doGetRequest(uri, uriHelper = uriHelper, bearerToken = bearerToken)
        if (response.statusCode == 200) {
            try {
                val json = HttpHelper.get.jsonDecodeUtf8(response)
                return MaybeError.value(Session.fromJson(json))
            } catch (e: Exception) {
                //
            }
        }
        return MaybeError.responseError(response)
    }

    suspend fun deleteUserSession(
        uriHelper: UriProductHelper = uriHelperFoodProd,
        bearerToken: String
    ): MaybeError<Boolean> {
        val uri = getUri(path = "/api/v1/session", uriHelper = uriHelper)
        val response =
            HttpHelper.get.doDeleteRequest(uri, uriHelper = uriHelper, bearerToken = bearerToken)
        if (response.statusCode == 204) {
            return MaybeError.value(true)
        }
        return MaybeError.responseError(response)
    }

    suspend fun createPrice(
        price: Price,
        bearerToken: String,
        uriHelper: UriProductHelper = uriHelperFoodProd
    ): MaybeError<Price?> {
        val uri = getUri(path = "/api/v1/prices", uriHelper = uriHelper)
        val body = mapOf(
            "price" to price.price,
            "currency" to price.currency.name,
            "date" to GetParametersHelper.formatDate(price.date),
            "product_code" to price.productCode,
            "product_name" to price.productName,
            "category_tag" to price.categoryTag,
            "labels_tags" to price.labelsTags,
            "origins_tags" to price.originsTags,
            "proof_id" to price.proofId,
            "price_per" to price.pricePer?.offTag,
            "price_without_discount" to price.priceWithoutDiscount,
            "price_is_discounted" to price.priceIsDiscounted,
            "discount_type" to price.discountType?.offTag,
            "location_osm_id" to price.locationOSMId,
            "location_osm_type" to price.locationOSMType?.offTag,
            "receipt_quantity" to price.receiptQuantity
        )
        val response = HttpHelper.get.doPostJsonRequest(
            uri,
            jsonEncode(body),
            uriHelper = uriHelper,
            bearerToken = bearerToken
        )
        if (response.statusCode == 201) {
            try {
                val json = HttpHelper.get.jsonDecodeUtf8(response)
                return MaybeError.value(Price.fromJson(json))
            } catch (e: Exception) {
                return MaybeError.unreadableResponse(response)
            }
        }
        return MaybeError.responseError(response)
    }

    suspend fun updatePrice(
        priceId: Int,
        parameters: UpdatePriceParameters,
        uriHelper: UriProductHelper = uriHelperFoodProd,
        bearerToken: String
    ): MaybeError<Boolean> {
        val uri = getUri(path = "/api/v1/prices/$priceId", uriHelper = uriHelper)
        val response = HttpHelper.get.doPatchRequest(
            uri,
            parameters.toJson(),
            null,
            uriHelper = uriHelper,
            bearerToken = bearerToken,
            addUserAgentParameters = false
        )
        if (response.statusCode == 200) {
            return MaybeError.value(true)
        }
        return MaybeError.responseError(response)
    }

    suspend fun deletePrice(
        priceId: Int,
        uriHelper: UriProductHelper = uriHelperFoodProd,
        bearerToken: String
    ): MaybeError<Boolean> {
        val uri = getUri(path = "/api/v1/prices/$priceId", uriHelper = uriHelper)
        val response =
            HttpHelper.get.doDeleteRequest(uri, uriHelper = uriHelper, bearerToken = bearerToken)
        if (response.statusCode == 204) {
            return MaybeError.value(true)
        }
        return MaybeError.responseError(response)
    }

    suspend fun getProofs(
        parameters: GetProofsParameters,
        uriHelper: UriProductHelper = uriHelperFoodProd,
        bearerToken: String
    ): MaybeError<GetProofsResult> {
        val uri = getUri(
            path = "/api/v1/proofs",
            queryParameters = parameters.getQueryParameters(),
            uriHelper = uriHelper
        )
        val response =
            HttpHelper.get.doGetRequest(uri, uriHelper = uriHelper, bearerToken = bearerToken)
        if (response.statusCode == 200) {
            try {
                val decodedResponse = HttpHelper.get.jsonDecodeUtf8(response)
                return MaybeError.value(GetProofsResult.fromJson(decodedResponse))
            } catch (e: Exception) {
                //
            }
        }
        return MaybeError.responseError(response)
    }

    suspend fun uploadProof(
        proofType: ProofType? = null,
        imageUri: Uri,
        mediaType: MediaType,
        createProofParameters: CreateProofParameters? = null,
        locationOSMId: Int? = null,
        locationOSMType: LocationOSMType? = null,
        date: DateTime? = null,
        currency: Currency? = null,
        receiptPriceCount: Int? = null,
        receiptPriceTotal: Double? = null,
        bearerToken: String,
        uriHelper: UriProductHelper = uriHelperFoodProd
    ): MaybeError<Proof> {
        val uri = getUri(path = "/api/v1/proofs/upload", uriHelper = uriHelper)
        val request = MultipartRequest("POST", uri)
        request.headers.putAll(
            mapOf(
                "Authorization" to "bearer $bearerToken",
                "Content-Type" to "multipart/form-data"
            )
        )
        if (createProofParameters != null) {
            request.fields.putAll(createProofParameters.toData())
        } else {
            request.fields.putAll(
                mapOfNotNull(
                    "type" to proofType!!.offTag,
                    "location_osm_id" to locationOSMId?.toString(),
                    "location_osm_type" to locationOSMType?.offTag,
                    "date" to date?.let { GetParametersHelper.formatDate(it) },
                    "currency" to currency?.name,
                    "receipt_price_count" to receiptPriceCount?.toString(),
                    "receipt_price_total" to receiptPriceTotal?.toString()
                )
            )
        }
        val fileBytes = UriReader.instance.readAsBytes(imageUri)
        val filename = basename(imageUri.toString())
        val multipartFile =
            MultipartFile.fromBytes("file", fileBytes, filename = filename, contentType = mediaType)
        request.files.add(multipartFile)
        val response = request.send()
        val responseBody = HttpHelper.get.extractResponseAsString(response)
        if (response.statusCode == 201) {
            try {
                val json = HttpHelper.get.jsonDecode(responseBody)
                return MaybeError.value(Proof.fromJson(json))
            } catch (e: Exception) {
                //
            }
        }
        return MaybeError.error(error = responseBody, statusCode = response.statusCode)
    }

    suspend fun getProof(
        proofId: Int,
        uriHelper: UriProductHelper = uriHelperFoodProd,
        bearerToken: String
    ): MaybeError<Proof> {
        val uri = getUri(path = "/api/v1/proofs/$proofId", uriHelper = uriHelper)
        val response =
            HttpHelper.get.doGetRequest(uri, uriHelper = uriHelper, bearerToken = bearerToken)
        if (response.statusCode == 200) {
            try {
                val decodedResponse = HttpHelper.get.jsonDecodeUtf8(response)
                return MaybeError.value(Proof.fromJson(decodedResponse))
            } catch (e: Exception) {
                //
            }
        }
        return MaybeError.responseError(response)
    }

    suspend fun updateProof(
        proofId: Int,
        parameters: UpdateProofParameters,
        uriHelper: UriProductHelper = uriHelperFoodProd,
        bearerToken: String
    ): MaybeError<Boolean> {
        val uri = getUri(path = "/api/v1/proofs/$proofId", uriHelper = uriHelper)
        val response = HttpHelper.get.doPatchRequest(
            uri,
            parameters.toJson(),
            null,
            uriHelper = uriHelper,
            bearerToken = bearerToken,
            addUserAgentParameters = false
        )
        if (response.statusCode == 200) {
            return MaybeError.value(true)
        }
        return MaybeError.responseError(response)
    }

    suspend fun deleteProof(
        proofId: Int,
        uriHelper: UriProductHelper = uriHelperFoodProd,
        bearerToken: String
    ): MaybeError<Boolean> {
        val uri = getUri(path = "/api/v1/proofs/$proofId", uriHelper = uriHelper)
        val response =
            HttpHelper.get.doDeleteRequest(uri, uriHelper = uriHelper, bearerToken = bearerToken)
        if (response.statusCode == 204) {
            return MaybeError.value(true)
        }
        return MaybeError.responseError(response)
    }

    suspend fun getUsers(
        parameters: GetUsersParameters,
        uriHelper: UriProductHelper = uriHelperFoodProd,
        bearerToken: String? = null
    ): MaybeError<GetUsersResult> {
        val uri = getUri(
            path = "/api/v1/users",
            queryParameters = parameters.getQueryParameters(),
            uriHelper = uriHelper
        )
        val response =
            HttpHelper.get.doGetRequest(uri, uriHelper = uriHelper, bearerToken = bearerToken)
        if (response.statusCode == 200) {
            try {
                val decodedResponse = HttpHelper.get.jsonDecodeUtf8(response)
                return MaybeError.value(GetUsersResult.fromJson(decodedResponse))
            } catch (e: Exception) {
                //
            }
        }
        return MaybeError.responseError(response)
    }

    suspend fun getStats(uriHelper: UriProductHelper = uriHelperFoodProd): MaybeError<PriceTotalStats> {
        val uri = getUri(path = "/api/v1/stats", uriHelper = uriHelper)
        val response = HttpHelper.get.doGetRequest(uri, uriHelper = uriHelper)
        if (response.statusCode == 200) {
            try {
                val decodedResponse = HttpHelper.get.jsonDecodeUtf8(response)
                return MaybeError.value(PriceTotalStats(decodedResponse))
            } catch (e: Exception) {
                //
            }
        }
        return MaybeError.responseError(response)
    }
}