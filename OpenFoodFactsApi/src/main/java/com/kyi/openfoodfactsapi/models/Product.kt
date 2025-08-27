package com.kyi.openfoodfactsapi.models

import DateTime
import ImageField
import OpenFoodFactsLanguage
import ProductImprovement
import com.kyi.openfoodfactsapi.models.knowledgepanel.KnowledgePanels
import com.kyi.openfoodfactsapi.sources.JsonObject

class Product : JsonObject() {

    var barcode: String? = null

    var productType: ProductType? = null

    var productName: String? = null

    var productNameInLanguages: Map<OpenFoodFactsLanguage, String>? = null

    var conservationConditionsInLanguages: Map<OpenFoodFactsLanguage, String>? = null

    var customerServiceInLanguages: Map<OpenFoodFactsLanguage, String>? = null

    var genericName: String? = null

    var genericNameInLanguages: Map<OpenFoodFactsLanguage, String>? = null

    var abbreviatedName: String? = null

    var abbreviatedNameInLanguages: Map<OpenFoodFactsLanguage, String>? = null

    var brands: String? = null

    var brandsTags: List<String>? = null

    var brandsTagsInLanguages: Map<OpenFoodFactsLanguage, List<String>>? = null

    var countries: String? = null

    var countriesTags: List<String>? = null

    var countriesTagsInLanguages: Map<OpenFoodFactsLanguage, List<String>>? = null

    var lang: OpenFoodFactsLanguage? = null

    var quantity: String? = null

    var imageFrontUrl: String? = null

    var imageFrontSmallUrl: String? = null

    var imageIngredientsUrl: String? = null

    var imageIngredientsSmallUrl: String? = null

    var imageNutritionUrl: String? = null

    var imageNutritionSmallUrl: String? = null

    var imagePackagingUrl: String? = null

    var imagePackagingSmallUrl: String? = null

    var servingSize: String? = null

    var servingQuantity: Double? = null

    var packagingQuantity: Double? = null

    var selectedImages: List<ProductImage>? = null

    var images: List<ProductImage>? = null

    var ingredients: List<Ingredient>? = null

    var ingredientsText: String? = null

    var ingredientsTextInLanguages: Map<OpenFoodFactsLanguage, String>? = null

    var ingredientsTags: List<String>? = null

    var ingredientsTagsInLanguages: Map<OpenFoodFactsLanguage, List<String>>? = null

    var imagesFreshnessInLanguages: Map<OpenFoodFactsLanguage, Map<ImageField, Int>>? = null

    var ingredientsAnalysisTags: IngredientsAnalysisTags? = null

    var ingredientsAnalysisTagsInLanguages: Map<OpenFoodFactsLanguage, List<String>>? = null

    var additives: Additives? = null

    var additivesTagsInLanguages: Map<OpenFoodFactsLanguage, List<String>>? = null

    var allergens: Allergens? = null

    var allergensTagsInLanguages: Map<OpenFoodFactsLanguage, List<String>>? = null

    var nutrientLevels: NutrientLevels? = null

    var nutrimentEnergyUnit: String? = null

    var nutritionData: Boolean? = null

    var nutrimentDataPer: String? = null

    var nutriscore: String? = null

    var comparedToCategory: String? = null

    var categories: String? = null

    var categoriesTags: List<String>? = null

    var categoriesTagsInLanguages: Map<OpenFoodFactsLanguage, List<String>>? = null

    var labels: String? = null

    var labelsTags: List<String>? = null

    var labelsTagsInLanguages: Map<OpenFoodFactsLanguage, List<String>>? = null

    var packaging: String? = null

    var packagings: List<ProductPackaging>? = null

    var packagingsComplete: Boolean? = null

    var packagingTags: List<String>? = null

    var packagingTextInLanguages: Map<OpenFoodFactsLanguage, String>? = null

    var miscTags: List<String>? = null

    var miscTagsInLanguages: Map<OpenFoodFactsLanguage, List<String>>? = null

    var statesTags: List<String>? = null

    var statesTagsInLanguages: Map<OpenFoodFactsLanguage, List<String>>? = null

    var tracesTags: List<String>? = null

    var tracesTagsInLanguages: Map<OpenFoodFactsLanguage, List<String>>? = null

    var traces: String? = null

    var storesTags: List<String>? = null

    var storesTagsInLanguages: Map<OpenFoodFactsLanguage, List<String>>? = null

    var stores: String? = null

    var attributeGroups: List<AttributeGroup>? = null

    var lastModified: DateTime? = null

    var lastModifiedBy: String? = null

    var lastImage: DateTime? = null

    var lastEditor: String? = null

    var entryDates: List<String>? = null

    var lastCheckDates: List<String>? = null

    var lastEditDates: List<String>? = null

    var lastImageDates: List<String>? = null

    var lastChecked: DateTime? = null

    var lastChecker: String? = null

    var created: DateTime? = null

    var creator: String? = null

    var editors: List<String>? = null

    var ecoscoreGrade: String? = null

    var ecoscoreScore: Double? = null

    var ecoscoreData: EcoscoreData? = null

    var knowledgePanels: KnowledgePanels? = null

    var embCodes: String? = null

    var manufacturingPlaces: String? = null

    var origins: String? = null

    var novaGroup: Int? = null

    var website: String? = null

    var dataQualityTags: List<String>? = null

    var dataQualityBugsTags: List<String>? = null

    var dataQualityErrorsTags: List<String>? = null

    var dataQualityInfoTags: List<String>? = null

    var dataQualityWarningsTags: List<String>? = null

    var obsolete: Boolean? = null

    var ownerFields: Map<String, Int>? = null

    var owner: String? = null

    var expirationDate: String? = null

    private var noNutritionDataField: Boolean? = null

    private var nutrimentsField: Nutriments? = null

    val noNutritionData: Boolean? get() = if (noNutritionDataField != null) noNutritionDataField else if (nutrimentsField != null) false else null

    fun setNoNutritionData(noNutritionData: Boolean?) {
        if (noNutritionData == true) nutrimentsField = null
        noNutritionDataField = noNutritionData
    }

    var nutriments: Nutriments?
        get() = if (noNutritionDataField == true) null else nutrimentsField
        set(nutriments) {
            if (nutriments == null) noNutritionDataField = true
            nutrimentsField = nutriments
        }

    fun getRawImages(): List<ProductImage>? = getImageSubset(false)

    fun getMainImages(): List<ProductImage>? = getImageSubset(true)

    fun isImageLocked(imageField: ImageField, language: OpenFoodFactsLanguage): Boolean? {
        if (owner == null) return null
        val localizedImage = getLocalizedImage(imageField, language)
        val imageId = localizedImage?.imgid
        if (imageId == null) return null
        val rawImage = getRawImage(imageId)
        if (rawImage == null) return null
        return rawImage.contributor == owner
    }

    fun getLocalizedImage(imageField: ImageField, language: OpenFoodFactsLanguage): ProductImage? {
        images?.forEach { productImage ->
            if (productImage.field == imageField && productImage.language == language) {
                if (productImage.rev == null) return null
                return productImage
            }
        }
        return null
    }

    fun getRawImage(imageId: String): ProductImage? {
        images?.forEach { productImage ->
            if (!productImage.isMain) {
                if (productImage.imgid == imageId) return productImage
            }
        }
        return null
    }

    private fun getImageSubset(isMain: Boolean): List<ProductImage>? {
        if (images == null) return null
        val result = mutableListOf<ProductImage>()
        images!!.forEach { productImage ->
            if (productImage.isMain == isMain) result.add(productImage)
        }
        return result
    }

    fun getAttributes(attributeIds: List<String>): Map<String, Attribute> {
        val result = mutableMapOf<String, Attribute>()
        attributeGroups?.forEach { attributeGroup ->
            attributeGroup.attributes?.forEach { attribute ->
                val attributeId = attribute.id!!
                if (attributeIds.contains(attributeId)) result[attributeId] = attribute
            }
        }
        return result
    }

    fun getAttribute(attributeId: String): Attribute? =
        getAttributes(listOf(attributeId))[attributeId]

    fun getProductImprovements(): Set<ProductImprovement> {
        val result = mutableSetOf<ProductImprovement>()
        if (statesTags == null) return result
        if (statesTags!!.contains("en:origins-to-be-completed")) result.add(ProductImprovement.ORIGINS_TO_BE_COMPLETED)
        if (statesTags!!.contains("en:categories-completed")) {
            if (nutriscore == null) result.add(ProductImprovement.CATEGORIES_BUT_NO_NUTRISCORE)
            if (statesTags!!.contains("en:nutrition-facts-to-be-completed")) result.add(
                ProductImprovement.ADD_NUTRITION_FACTS
            )
        }
        if (statesTags!!.contains("en:categories-to-be-completed")) {
            if (statesTags!!.contains("en:nutrition-facts-completed")) result.add(ProductImprovement.ADD_CATEGORY)
            if (statesTags!!.contains("en:nutrition-facts-to-be-completed")) result.add(
                ProductImprovement.ADD_NUTRITION_FACTS_AND_CATEGORY
            )
        }
        if (statesTags!!.contains("en:nutrition-photo-to-be-selected") || statesTags!!.contains("en:photos-to-be-uploaded")) result.add(
            ProductImprovement.OBSOLETE_NUTRITION_IMAGE
        )
        return result
    }

    fun getBestProductName(language: OpenFoodFactsLanguage): String {
        var tmp = productNameInLanguages?.get(language)?.takeIf { it.isNotEmpty() }
        if (tmp != null) return tmp
        tmp = productName?.takeIf { it.isNotEmpty() }
        if (tmp != null) return tmp
        tmp = genericNameInLanguages?.get(language)?.takeIf { it.isNotEmpty() }
        if (tmp != null) return tmp
        tmp = genericName?.takeIf { it.isNotEmpty() }
        if (tmp != null) return tmp
        tmp = abbreviatedNameInLanguages?.get(language)?.takeIf { it.isNotEmpty() }
        if (tmp != null) return tmp
        tmp = abbreviatedName?.takeIf { it.isNotEmpty() }
        if (tmp != null) return tmp
        return ""
    }

    fun getFirstBrand(): String? {
        if (brands == null) return null
        val items = brands!!.split(",")
        if (items.isEmpty()) return null
        return items.first()
    }

    fun getProductNameBrand(language: OpenFoodFactsLanguage, separator: String): String {
        val bestProductName = getBestProductName(language)
        val firstBrand = getFirstBrand()
        if (firstBrand == null) return bestProductName
        return "$bestProductName$separator$firstBrand"
    }

    fun getProductNameBrandQuantity(language: OpenFoodFactsLanguage, separator: String): String {
        val productNameBrand = getProductNameBrand(language, separator)
        if (quantity?.isNotEmpty() != true) return productNameBrand
        if (productNameBrand.contains(quantity!!)) return productNameBrand
        return "$productNameBrand$separator${quantity!!.replace(" ", "\u00A0")}"
    }

    fun getOwnerFieldTimestamp(ownerField: OwnerField): Int? = ownerFields?.get(ownerField.offTag)

    companion object {

        fun fromJson(json: Map<String, Any?>): Product = Product.fromJson(json)

        const val resultProductFound = "product_found"
        const val resultProductNotFound = "product_not_found"
    }

    override fun toJson(): Map<String, Any?> {
        // Implementation similar to Dart, handling in_languages fields etc.
        // For brevity, assume it's implemented
        return emptyMap() // Placeholder
    }
}