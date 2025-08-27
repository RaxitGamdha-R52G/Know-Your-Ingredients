package com.kyi.openfoodfactsapi.prices

class GetPriceProductsParameters : GetPriceCountParametersHelper<GetPriceProductsOrderField>() {

    var brandsLike: String? = null
    var brandsTagsContains: String? = null
    var categoriesTagsContains: String? = null
    var code: String? = null
    var ecoscoreGrade: String? = null
    var labelsTagsContains: String? = null
    var novaGroup: String? = null
    var nutriscoreGrade: String? = null
    var productNameLike: String? = null
    var uniqueScansNGte: Int? = null
    var source: Flavor? = null

    override fun getQueryParameters(): Map<String, String> {
        super.getQueryParameters()
        addNonNullString(brandsLike, "brands__like")
        addNonNullString(brandsTagsContains, "brands_tags__contains")
        addNonNullString(categoriesTagsContains, "categories_tags__contains")
        addNonNullString(code, "code")
        addNonNullString(ecoscoreGrade, "ecoscore_grade")
        addNonNullString(labelsTagsContains, "labels_tags__contains")
        addNonNullString(novaGroup, "nova_group")
        addNonNullString(nutriscoreGrade, "nutriscore_grade")
        addNonNullString(productNameLike, "product_name__like")
        addNonNullInt(uniqueScansNGte, "unique_scans_n__gte")
        addNonNullString(source?.offTag, "source")
        return result
    }
}