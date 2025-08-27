package com.kyi.openfoodfactsapi.prices

class GetProofsParameters : GetPriceCountParametersHelper<GetProofsOrderField>() {

    var owner: String? = null
    var type: ProofType? = null
    var locationOSMId: Int? = null
    var locationOSMType: LocationOSMType? = null
    var locationId: Int? = null
    var currency: Currency? = null
    var date: DateTime? = null
    var dateGt: DateTime? = null
    var dateGte: DateTime? = null
    var dateLt: DateTime? = null
    var dateLte: DateTime? = null
    var kind: ContributionKind? = null

    override fun getQueryParameters(): Map<String, String> {
        super.getQueryParameters()
        addNonNullString(owner, "owner")
        addNonNullString(type?.offTag, "type")
        addNonNullInt(locationOSMId, "location_osm_id")
        addNonNullString(locationOSMType?.offTag, "location_osm_type")
        addNonNullInt(locationId, "location_id")
        addNonNullString(currency?.name, "currency")
        addNonNullDate(date, "date", dayOnly = true)
        addNonNullDate(dateGt, "date__gt", dayOnly = true)
        addNonNullDate(dateGte, "date__gte", dayOnly = true)
        addNonNullDate(dateLt, "date__lt", dayOnly = true)
        addNonNullDate(dateLte, "date__lte", dayOnly = true)
        addNonNullString(kind?.offTag, "kind")
        return result
    }
}