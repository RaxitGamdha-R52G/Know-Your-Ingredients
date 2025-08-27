package com.kyi.openfoodfactsapi.prices

class GetPricesParameters : GetParametersHelper<GetPricesOrderField>() {

    var productCode: String? = null
    var productId: Int? = null
    var productIdIsNull: Boolean? = null
    var categoryTag: String? = null
    var labelsTagsLike: String? = null
    var originsTagsLike: String? = null
    var locationOSMId: Int? = null
    var locationOSMType: LocationOSMType? = null
    var locationId: Int? = null
    var currency: Currency? = null
    var date: DateTime? = null
    var dateGt: DateTime? = null
    var dateGte: DateTime? = null
    var dateLt: DateTime? = null
    var dateLte: DateTime? = null
    var proofId: Int? = null
    var owner: String? = null
    var createdGte: DateTime? = null
    var createdLte: DateTime? = null
    var kind: ContributionKind? = null

    override fun getQueryParameters(): Map<String, String> {
        super.getQueryParameters()
        addNonNullString(productCode, "product_code")
        addNonNullInt(productId, "product_id")
        addNonNullBool(productIdIsNull, "product_id__isnull")
        addNonNullString(categoryTag, "category_tag")
        addNonNullString(labelsTagsLike, "labels_tags__like")
        addNonNullString(originsTagsLike, "origins_tags__like")
        addNonNullInt(locationOSMId, "location_osm_id")
        addNonNullString(locationOSMType?.offTag, "location_osm_type")
        addNonNullInt(locationId, "location_id")
        addNonNullString(currency?.name, "currency")
        addNonNullDate(date, "date", dayOnly = true)
        addNonNullDate(dateGt, "date__gt", dayOnly = true)
        addNonNullDate(dateGte, "date__gte", dayOnly = true)
        addNonNullDate(dateLt, "date__lt", dayOnly = true)
        addNonNullDate(dateLte, "date__lte", dayOnly = true)
        addNonNullInt(proofId, "proof_id")
        addNonNullString(owner, "owner")
        addNonNullDate(createdGte, "created__gte", dayOnly = false)
        addNonNullDate(createdLte, "created__lte", dayOnly = false)
        addNonNullString(kind?.offTag, "kind")
        return result
    }
}