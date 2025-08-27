package com.kyi.openfoodfactsapi

import ImageAngle
import ImageField
import TaxonomyAdditiveQueryConfiguration
import TaxonomyAllergenQueryConfiguration
import TaxonomyCategoryQueryConfiguration
import TaxonomyCountryQueryConfiguration
import TaxonomyIngredientQueryConfiguration
import TaxonomyLabelQueryConfiguration
import TaxonomyLanguageQueryConfiguration
import TaxonomyNovaQueryConfiguration
import TaxonomyOriginQueryConfiguration
import TaxonomyPackagingMaterialQueryConfiguration
import TaxonomyPackagingRecyclingQueryConfiguration
import TaxonomyPackagingShapeQueryConfiguration
import com.kyi.openfoodfactsapi.models.OcrIngredientsResult
import com.kyi.openfoodfactsapi.models.OcrPackagingResult
import com.kyi.openfoodfactsapi.models.OrderedNutrients
import com.kyi.openfoodfactsapi.models.PerSize
import com.kyi.openfoodfactsapi.models.Product
import com.kyi.openfoodfactsapi.models.ProductFreshness
import com.kyi.openfoodfactsapi.models.ProductPackaging
import com.kyi.openfoodfactsapi.models.ProductResultV3
import com.kyi.openfoodfactsapi.models.SearchResult
import com.kyi.openfoodfactsapi.models.SendImage
import com.kyi.openfoodfactsapi.models.Status
import com.kyi.openfoodfactsapi.models.User
import com.kyi.openfoodfactsapi.models.parameter.BarcodeParameter
import com.kyi.openfoodfactsapi.models.taxonomy.TaxonomyAdditive
import com.kyi.openfoodfactsapi.models.taxonomy.TaxonomyAllergen
import com.kyi.openfoodfactsapi.models.taxonomy.TaxonomyCategory
import com.kyi.openfoodfactsapi.models.taxonomy.TaxonomyCountry
import com.kyi.openfoodfactsapi.models.taxonomy.TaxonomyIngredient
import com.kyi.openfoodfactsapi.models.taxonomy.TaxonomyLabel
import com.kyi.openfoodfactsapi.models.taxonomy.TaxonomyLanguage
import com.kyi.openfoodfactsapi.models.taxonomy.TaxonomyNova
import com.kyi.openfoodfactsapi.models.taxonomy.TaxonomyOrigin
import com.kyi.openfoodfactsapi.models.taxonomy.TaxonomyPackaging
import com.kyi.openfoodfactsapi.models.taxonomy.TaxonomyPackagingMaterial
import com.kyi.openfoodfactsapi.models.taxonomy.TaxonomyPackagingRecycling
import com.kyi.openfoodfactsapi.models.taxonomy.TaxonomyPackagingShape
import com.kyi.openfoodfactsapi.sources.JsonObject
import com.kyi.openfoodfactsapi.utils.AbstractQueryConfiguration
import com.kyi.openfoodfactsapi.utils.HttpHelper
import com.kyi.openfoodfactsapi.utils.OcrField
import com.kyi.openfoodfactsapi.utils.OpenFoodFactsCountry
import com.kyi.openfoodfactsapi.utils.OpenFoodFactsLanguage
import com.kyi.openfoodfactsapi.utils.ProductField
import com.kyi.openfoodfactsapi.utils.ProductHelper
import com.kyi.openfoodfactsapi.utils.ProductQueryConfiguration
import com.kyi.openfoodfactsapi.utils.ProductQueryVersion
import com.kyi.openfoodfactsapi.utils.ProductSearchQueryConfiguration
import com.kyi.openfoodfactsapi.utils.TagType
import com.kyi.openfoodfactsapi.utils.TaxonomyQueryConfiguration
import com.kyi.openfoodfactsapi.utils.TooManyRequestsException
import com.kyi.openfoodfactsapi.utils.UriHelper
import com.kyi.openfoodfactsapi.utils.UriProductHelper
import com.kyi.openfoodfactsapi.utils.uriHelperFoodProd

object OpenFoodAPIClient {

    suspend fun saveProduct(
        user: User,
        product: Product,
        uriHelper: UriProductHelper = uriHelperFoodProd,
        country: OpenFoodFactsCountry? = null,
        language: OpenFoodFactsLanguage? = null
    ): Status {
        val parameterMap = mutableMapOf<String, String>()
        parameterMap.putAll(user.toData())
        parameterMap.putAll(product.toServerData())
        language?.let { parameterMap["lc"] = it.offTag }
        country?.let { parameterMap["cc"] = it.offTag }

        if (product.nutriments != null) {
            val rawNutrients = product.nutriments!!.toData()
            for ((key, value) in rawNutrients) {
                var newKey = "nutriment_$key"
                for (option in PerSize.entries) {
                    val pos = newKey.indexOf("_${option.offTag}")
                    if (pos != -1) {
                        newKey = newKey.substring(0, pos)
                    }
                }
                parameterMap[newKey] = value
            }
        }
        parameterMap.remove("nutriments")

        val productUri = uriHelper.getPostUri(path = "/cgi/product_jqm2.pl")

        val response = HttpHelper.get.doPostRequest(
            productUri,
            parameterMap,
            user,
            uriHelper = uriHelper,
            addCredentialsToBody = true
        )
        return Status.fromApiResponse(response.body)
    }

    suspend fun temporarySaveProductV3(
        user: User,
        barcode: String,
        packagings: List<ProductPackaging>? = null,
        packagingsComplete: Boolean? = null,
        uriHelper: UriProductHelper = uriHelperFoodProd,
        country: OpenFoodFactsCountry? = null,
        language: OpenFoodFactsLanguage? = null
    ): ProductResultV3 {
        val parameterMap = mutableMapOf<String, Any>()
        parameterMap.putAll(user.toData())
        if (packagings == null && packagingsComplete == null) throw Exception("At least one V3 field must be populated.")
        parameterMap["product"] = mutableMapOf<String, Any>()
        packagings?.let { parameterMap["product"]!![ProductField.PACKAGINGS.offTag] = it }
        packagingsComplete?.let {
            parameterMap["product"]!![ProductField.PACKAGINGS_COMPLETE.offTag] = it
        }
        language?.let { parameterMap["lc"] = it.offTag }
        country?.let { parameterMap["cc"] = it.offTag }

        val productUri = uriHelper.getPatchUri(path = "/api/v3/product/$barcode")

        val response = HttpHelper.get.doPatchRequest(
            productUri,
            parameterMap,
            user,
            uriHelper = uriHelper
        )
        return ProductResultV3.fromJson(HttpHelper.get.jsonDecode(response.body))
    }

    suspend fun addProductImage(
        user: User,
        image: SendImage,
        uriHelper: UriProductHelper = uriHelperFoodProd
    ): Status {
        val dataMap = mutableMapOf<String, String>()
        val fileMap = mutableMapOf<String, Uri>()
        dataMap.putAll(image.toData())
        fileMap[image.getImageDataKey()] = image.imageUri
        val imageUri =
            uriHelper.getUri(path = "/cgi/product_image_upload.pl", addUserAgentParameters = false)
        return HttpHelper.get.doMultipartRequest(
            imageUri,
            dataMap,
            files = fileMap,
            user = user,
            uriHelper = uriHelper
        )
    }

    suspend fun getProductV3(
        configuration: ProductQueryConfiguration,
        user: User? = null,
        uriHelper: UriProductHelper = uriHelperFoodProd
    ): ProductResultV3 {
        if (!configuration.matchesV3()) throw Exception("The configuration must match V3!")
        val productString = getProductString(configuration, user = user, uriHelper = uriHelper)
        val jsonStr = replaceQuotes(productString)
        val result = ProductResultV3.fromJson(HttpHelper.get.jsonDecode(jsonStr))
        result.product?.let {
            ProductHelper.removeImages(it, configuration.language)
            ProductHelper.createImageUrls(it, uriHelper = uriHelper)
        }
        return result
    }

    suspend fun getProductString(
        configuration: ProductQueryConfiguration,
        user: User? = null,
        uriHelper: UriProductHelper = uriHelperFoodProd
    ): String {
        val response = configuration.getResponse(user, uriHelper)
        TooManyRequestsException.check(response)
        return response.body
    }

    fun getProductUri(
        barcode: String,
        language: OpenFoodFactsLanguage? = null,
        country: OpenFoodFactsCountry? = null,
        uriHelper: UriProductHelper = uriHelperFoodProd,
        replaceSubdomain: Boolean = true
    ): Uri {
        val uri = uriHelper.getUri(path = "product/$barcode", addUserAgentParameters = false)
        if (!replaceSubdomain) return uri
        return UriHelper.replaceSubdomain(uri, language = language, country = country)
    }

    fun getTaxonomyTranslationUri(
        taxonomyTagType: TagType,
        language: OpenFoodFactsLanguage,
        uriHelper: UriProductHelper = uriHelperFoodProd,
        replaceSubdomain: Boolean = true
    ): Uri {
        if (taxonomyTagType == TagType.EMB_CODES) throw Exception("No taxonomy translation for $taxonomyTagType")
        val uri = uriHelper.getUri(
            path = taxonomyTagType.offTag,
            queryParameters = mapOf("translate" to "1"),
            addUserAgentParameters = false
        )
        if (!replaceSubdomain) return uri
        return UriHelper.replaceSubdomainWithCodes(uri, languageCode = language.offTag)
    }

    fun getCrowdinUri(language: OpenFoodFactsLanguage): Uri =
        Uri.parse("https://crowdin.com/project/openfoodfacts/${language.offTag}")

    suspend fun searchProducts(
        user: User?,
        configuration: AbstractQueryConfiguration,
        uriHelper: UriProductHelper = uriHelperFoodProd
    ): SearchResult {
        val response = configuration.getResponse(user, uriHelper)
        TooManyRequestsException.check(response)
        val jsonStr = replaceQuotes(response.body)
        val result = SearchResult.fromJson(HttpHelper.get.jsonDecode(jsonStr))
        removeImages(result, configuration)
        return result
    }

    suspend fun getProductFreshness(
        barcodes: List<String>,
        version: ProductQueryVersion,
        user: User? = null,
        language: OpenFoodFactsLanguage? = null,
        country: OpenFoodFactsCountry? = null,
        uriHelper: UriProductHelper = uriHelperFoodProd
    ): Map<String, ProductFreshness> {
        val searchResult = searchProducts(
            user,
            ProductSearchQueryConfiguration(
                parametersList = listOf(BarcodeParameter.list(barcodes)),
                language = language,
                country = country,
                fields = listOf(
                    ProductField.BARCODE,
                    ProductField.ECOSCORE_SCORE,
                    ProductField.NUTRISCORE,
                    ProductField.INGREDIENTS_TAGS,
                    ProductField.LAST_MODIFIED,
                    ProductField.STATES_TAGS
                ),
                version = version
            ),
            uriHelper = uriHelper
        )
        val result = mutableMapOf<String, ProductFreshness>()
        searchResult.products?.let { products ->
            for (product in products) {
                result[product.barcode!!] = ProductFreshness.fromProduct(product)
            }
        }
        return result
    }

    suspend fun <T : JsonObject, F : Enum<*>> getTaxonomy(
        configuration: TaxonomyQueryConfiguration<T, F>,
        user: User? = null,
        uriHelper: UriProductHelper = uriHelperFoodProd
    ): Map<String, T>? {
        val uri = configuration.getPostUri(uriHelper)
        val response = HttpHelper.get.doPostRequest(
            uri,
            configuration.getParametersMap(),
            user,
            uriHelper = uriHelper,
            addCredentialsToBody = false
        )
        var decodedJson =
            HttpHelper.get.jsonDecode(replaceQuotes(response.body)) as Map<String, Any?>
        decodedJson = decodedJson.filter { (_, value) ->
            when (value) {
                is Map<*, *> -> value.isNotEmpty()
                is List<*> -> value.isNotEmpty()
                else -> true
            }
        }
        if (decodedJson.isEmpty()) return null
        return configuration.convertResults(decodedJson)
    }

    suspend fun getTaxonomyPackagingShapes(
        configuration: TaxonomyPackagingShapeQueryConfiguration,
        user: User? = null,
        uriHelper: UriProductHelper = uriHelperFoodProd
    ): Map<String, TaxonomyPackagingShape>? =
        getTaxonomy(configuration, user = user, uriHelper = uriHelper)

    suspend fun getTaxonomyPackagingMaterials(
        configuration: TaxonomyPackagingMaterialQueryConfiguration,
        user: User? = null,
        uriHelper: UriProductHelper = uriHelperFoodProd
    ): Map<String, TaxonomyPackagingMaterial>? =
        getTaxonomy(configuration, user = user, uriHelper = uriHelper)

    suspend fun getTaxonomyPackagingRecycling(
        configuration: TaxonomyPackagingRecyclingQueryConfiguration,
        user: User? = null,
        uriHelper: UriProductHelper = uriHelperFoodProd
    ): Map<String, TaxonomyPackagingRecycling>? =
        getTaxonomy(configuration, user = user, uriHelper = uriHelper)

    suspend fun getTaxonomyNova(
        configuration: TaxonomyNovaQueryConfiguration,
        user: User? = null,
        uriHelper: UriProductHelper = uriHelperFoodProd
    ): Map<String, TaxonomyNova>? =
        getTaxonomy(configuration, user = user, uriHelper = uriHelper)

    suspend fun getTaxonomyCategories(
        configuration: TaxonomyCategoryQueryConfiguration,
        user: User? = null,
        uriHelper: UriProductHelper = uriHelperFoodProd
    ): Map<String, TaxonomyCategory>? =
        getTaxonomy(configuration, user = user, uriHelper = uriHelper)

    suspend fun getTaxonomyAdditives(
        configuration: TaxonomyAdditiveQueryConfiguration,
        user: User? = null,
        uriHelper: UriProductHelper = uriHelperFoodProd
    ): Map<String, TaxonomyAdditive>? =
        getTaxonomy(configuration, user = user, uriHelper = uriHelper)

    suspend fun getTaxonomyAllergens(
        configuration: TaxonomyAllergenQueryConfiguration,
        user: User? = null,
        uriHelper: UriProductHelper = uriHelperFoodProd
    ): Map<String, TaxonomyAllergen>? =
        getTaxonomy(configuration, user = user, uriHelper = uriHelper)

    suspend fun getTaxonomyCountries(
        configuration: TaxonomyCountryQueryConfiguration,
        user: User? = null,
        uriHelper: UriProductHelper = uriHelperFoodProd
    ): Map<String, TaxonomyCountry>? =
        getTaxonomy(configuration, user = user, uriHelper = uriHelper)

    suspend fun getTaxonomyIngredients(
        configuration: TaxonomyIngredientQueryConfiguration,
        user: User? = null,
        uriHelper: UriProductHelper = uriHelperFoodProd
    ): Map<String, TaxonomyIngredient>? =
        getTaxonomy(configuration, user = user, uriHelper = uriHelper)

    suspend fun getTaxonomyLabels(
        configuration: TaxonomyLabelQueryConfiguration,
        user: User? = null,
        uriHelper: UriProductHelper = uriHelperFoodProd
    ): Map<String, TaxonomyLabel>? =
        getTaxonomy(configuration, user = user, uriHelper = uriHelper)

    suspend fun getTaxonomyLanguages(
        configuration: TaxonomyLanguageQueryConfiguration,
        user: User? = null,
        uriHelper: UriProductHelper = uriHelperFoodProd
    ): Map<String, TaxonomyLanguage>? =
        getTaxonomy(configuration, user = user, uriHelper = uriHelper)

    suspend fun getTaxonomyPackagings(
        configuration: TaxonomyPackagingQueryConfiguration,
        user: User? = null,
        uriHelper: UriProductHelper = uriHelperFoodProd
    ): Map<String, TaxonomyPackaging>? =
        getTaxonomy(configuration, user = user, uriHelper = uriHelper)

    suspend fun getTaxonomyOrigins(
        configuration: TaxonomyOriginQueryConfiguration,
        user: User? = null,
        uriHelper: UriProductHelper = uriHelperFoodProd
    ): Map<String, TaxonomyOrigin>? =
        getTaxonomy(configuration, user = user, uriHelper = uriHelper)

    private fun removeImages(
        searchResult: SearchResult,
        configuration: AbstractQueryConfiguration
    ) {
        searchResult.products?.forEach { ProductHelper.removeImages(it, configuration.language) }
    }

    suspend fun extractIngredients(
        user: User,
        barcode: String,
        language: OpenFoodFactsLanguage,
        ocrField: OcrField = OcrField.GOOGLE_CLOUD_VISION,
        uriHelper: UriProductHelper = uriHelperFoodProd
    ): OcrIngredientsResult {
        val uri = uriHelper.getPostUri(path = "/cgi/ingredients.pl")
        val queryParameters = mapOf(
            "code" to barcode,
            "process_image" to "1",
            "id" to "ingredients_${language.offTag}",
            "ocr_engine" to ocrField.offTag
        )
        val response = HttpHelper.get.doPostRequest(
            uri,
            queryParameters,
            user,
            uriHelper = uriHelper,
            addCredentialsToBody = false
        )
        return OcrIngredientsResult.fromJson(HttpHelper.get.jsonDecodeUtf8(response))
    }

    suspend fun extractPackaging(
        user: User,
        barcode: String,
        language: OpenFoodFactsLanguage,
        ocrField: OcrField = OcrField.GOOGLE_CLOUD_VISION,
        uriHelper: UriProductHelper = uriHelperFoodProd
    ): OcrPackagingResult {
        val uri = uriHelper.getPostUri(path = "/cgi/packaging.pl")
        val queryParameters = mapOf(
            "code" to barcode,
            "process_image" to "1",
            "id" to "packaging_${language.offTag}",
            "ocr_engine" to ocrField.offTag
        )
        val response = HttpHelper.get.doPostRequest(
            uri,
            queryParameters,
            user,
            uriHelper = uriHelper,
            addCredentialsToBody = false
        )
        return OcrPackagingResult.fromJson(HttpHelper.get.jsonDecodeUtf8(response))
    }

    suspend fun getOrderedNutrients(
        country: OpenFoodFactsCountry,
        language: OpenFoodFactsLanguage,
        uriHelper: UriProductHelper = uriHelperFoodProd
    ): OrderedNutrients {
        return OrderedNutrients.fromJson(
            HttpHelper.get.jsonDecode(
                await getOrderedNutrientsJsonString (country = country,
                language = language,
                uriHelper = uriHelper
            )
        ))
    }

    suspend fun getOrderedNutrientsJsonString(
        country: OpenFoodFactsCountry,
        language: OpenFoodFactsLanguage,
        uriHelper: UriProductHelper = uriHelperFoodProd
    ): String {
        val uri = uriHelper.getPostUri(path = "cgi/nutrients.pl")
        val queryParameters = mapOf(
            "cc" to country.offTag,
            "lc" to language.offTag
        )
        val response = HttpHelper.get.doPostRequest(
            uri,
            queryParameters,
            null,
            uriHelper = uriHelper,
            addCredentialsToBody = false
        )
        if (response.statusCode != 200) throw Exception("Could not retrieve ordered nutrients!")
        return response.body
    }

    suspend fun setProductImageAngle(
        barcode: String,
        imageField: ImageField,
        language: OpenFoodFactsLanguage,
        imgid: String,
        angle: ImageAngle,
        user: User,
        uriHelper: UriProductHelper = uriHelperFoodProd
    ): String? =
        callProductImageCrop(
            barcode = barcode,
            imageField = imageField,
            language = language,
            imgid = imgid,
            user = user,
            extraParameters = mapOf("angle" to angle.degreesClockwise),
            uriHelper = uriHelper
        )

    suspend fun setProductImageCrop(
        barcode: String,
        imageField: ImageField,
        language: OpenFoodFactsLanguage,
        imgid: String,
        x1: Int,
        y1: Int,
        x2: Int,
        y2: Int,
        user: User,
        angle: ImageAngle = ImageAngle.NOON,
        uriHelper: UriProductHelper = uriHelperFoodProd
    ): String? =
        callProductImageCrop(
            barcode = barcode,
            imageField = imageField,
            language = language,
            imgid = imgid,
            user = user,
            extraParameters = mapOf(
                "x1" to x1.toString(),
                "y1" to y1.toString(),
                "x2" to x2.toString(),
                "y2" to y2.toString(),
                "angle" to angle.degreesClockwise,
                "coordinates_image_size" to "full"
            ),
            uriHelper = uriHelper
        )

    private suspend fun callProductImageCrop(
        barcode: String,
        imageField: ImageField,
        language: OpenFoodFactsLanguage,
        imgid: String,
        user: User,
        extraParameters: Map<String, String>,
        uriHelper: UriProductHelper
    ): String? {
        val id = "${imageField.offTag}_${language.offTag}"
        val queryParameters = mutableMapOf<String, String>(
            "code" to barcode,
            "id" to id,
            "imgid" to imgid
        )
        queryParameters.putAll(extraParameters)
        val uri = uriHelper.getPostUri(path = "cgi/product_image_crop.pl")
        val response = HttpHelper.get.doPostRequest(
            uri,
            queryParameters,
            user,
            uriHelper = uriHelper,
            addCredentialsToBody = true
        )
        if (response.statusCode != 200) throw Exception("Bad response (${response.statusCode}): ${response.body}")
        val json = HttpHelper.get.jsonDecode(response.body) as Map<String, Any?>
        val status = json["status"] as String
        if (status != "status ok") throw Exception("Status not ok ($status)")
        val imagefield = json["imagefield"] as String
        if (imagefield != id) throw Exception("Different imagefield: expected \"$id\", actual \"$imagefield\"")
        val images = json["image"] as Map<String, Any?>
        val filename = images["display_url"] as String?
        filename ?: return null
        return "${uriHelper.getProductImageRootUrl(barcode)}/$filename"
    }

    suspend fun unselectProductImage(
        barcode: String,
        imageField: ImageField,
        language: OpenFoodFactsLanguage,
        user: User,
        uriHelper: UriProductHelper = uriHelperFoodProd
    ) {
        val id = "${imageField.offTag}_${language.offTag}"
        val queryParameters = mapOf("code" to barcode, "id" to id)
        val uri = uriHelper.getPostUri(path = "cgi/product_image_unselect.pl")
        val response = HttpHelper.get.doPostRequest(
            uri,
            queryParameters,
            user,
            uriHelper = uriHelper,
            addCredentialsToBody = true
        )
        if (response.statusCode != 200) throw Exception("Bad response (${response.statusCode}): ${response.body}")
        val json = HttpHelper.get.jsonDecode(response.body) as Map<String, Any?>
        val status = json["status"] as String
        if (status != "status ok") throw Exception("Status not ok ($status)")
        val statusCode = json["status_code"] as Int
        if (statusCode != 0) throw Exception("Status Code not ok ($statusCode)")
        val imagefield = json["imagefield"] as String
        if (imagefield != id) throw Exception("Different imagefield: expected \"$id\", actual \"$imagefield\"")
    }

    suspend fun getLocalizedCountryNames(
        language: OpenFoodFactsLanguage,
        uriHelper: UriProductHelper = uriHelperFoodProd
    ): Map<OpenFoodFactsCountry, String> {
        val response = HttpHelper.get.doGetRequest(
            uriHelper.getUri(
                path = "/cgi/countries.pl",
                queryParameters = mapOf("lc" to language.offTag)
            ),
            uriHelper = uriHelper
        )
        val result = mutableMapOf<OpenFoodFactsCountry, String>()
        val map = HttpHelper.get.jsonDecode(response.body) as Map<String, Any?>
        for (countryCode in map.keys) {
            val country = OpenFoodFactsCountry.fromOffTag(countryCode) ?: continue
            result[country] = map[countryCode] as String
        }
        return result
    }

    private fun replaceQuotes(str: String): String = str.replace("&quot;", "\\\"")
}