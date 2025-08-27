package com.kyi.openfoodfactsapi.prices

class GetLocationsParameters : GetPriceCountParametersHelper<GetLocationsOrderField>() {

    var osmNameLike: String? = null
    var osmCityLike: String? = null
    var osmPostcodeLike: String? = null
    var osmCountryLike: String? = null

    override fun getQueryParameters(): Map<String, String> {
        super.getQueryParameters()
        addNonNullString(osmNameLike, "osm_name__like")
        addNonNullString(osmCityLike, "osm_address_city__like")
        addNonNullString(osmPostcodeLike, "osm_address_postcode__like")
        addNonNullString(osmCountryLike, "osm_address_country__like")
        return result
    }
}