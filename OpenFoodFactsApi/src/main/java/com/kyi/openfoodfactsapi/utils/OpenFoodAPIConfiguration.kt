package com.kyi.openfoodfactsapi.utils

import com.kyi.openfoodfactsapi.models.User
import com.kyi.openfoodfactsapi.models.UserAgent

class OpenFoodAPIConfiguration private constructor() {

    companion object {

        var userAgent: UserAgent? = null

        var uuid: String? = null

        var globalUser: User? = null

        var globalLanguages: List<OpenFoodFactsLanguage>? = null

        var globalCountry: OpenFoodFactsCountry? = null

        fun getUser(user: User?): User? = user ?: globalUser

        fun computeCountryCode(country: OpenFoodFactsCountry?, cc: String?): String? {
            if (country != null) return country.offTag
            if (globalCountry != null) return globalCountry!!.offTag
            if (cc != null) return cc
            return null
        }
    }
}

val uriHelperFoodProd = UriProductHelper(domain = "openfoodfacts.org")

val uriHelperFoodTest = UriProductHelper(domain = "openfoodfacts.net", userInfoForPatch = HttpHelper.userInfoForTest, isTestMode = true)

val uriHelperRobotoffProd = UriHelper(host = "robotoff.openfoodfacts.org")

val uriHelperRobotoffTest = UriHelper(host = "robotoff.openfoodfacts.net", isTestMode = true)

val uriHelperFolksonomyProd = UriHelper(host = "api.folksonomy.openfoodfacts.org")

val uriHelperFolksonomyTest = UriHelper(host = "api.folksonomy.openfoodfacts.net", isTestMode = true)

val uriHelperEventsProd = UriHelper(host = "events.openfoodfacts.org")

val uriHelperEventsTest = UriHelper(host = "events.openfoodfacts.net", isTestMode = true)