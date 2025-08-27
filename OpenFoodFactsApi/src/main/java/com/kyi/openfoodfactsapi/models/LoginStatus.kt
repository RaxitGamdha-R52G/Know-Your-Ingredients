package com.kyi.openfoodfactsapi.models

import JsonHelper

class LoginStatus(
    val status: Int,
    val statusVerbose: String,
    val userName: String? = null,
    val userId: String? = null,
    val preferredLanguage: OpenFoodFactsLanguage? = null,
    val country: OpenFoodFactsCountry? = null,
    val isModerator: Boolean? = null,
    val isAdmin: Boolean? = null,
    val cookie: String? = null
) {

    companion object {

        fun fromJson(json: Map<String, Any?>, headers: Map<String, String>?): LoginStatus {
            val details = json["user"] as Map<String, Any?>?
            return LoginStatus(
                status = parseInt(json["status"])!!,
                statusVerbose = json["status_verbose"] as String,
                userId = json["user_id"] as String?,
                userName = details?.get("name") as String?,
                preferredLanguage = OpenFoodFactsLanguage.fromOffTag(details?.get("preferred_language") as String?),
                country = OpenFoodFactsCountry.fromOffTag(details?.get("cc") as String?),
                isModerator = JsonHelper.boolFromJSON(details?.get("moderator")),
                isAdmin = JsonHelper.boolFromJSON(details?.get("admin")),
                cookie = headers?.get("set-cookie")
            )
        }
    }

    val successful: Boolean get() = status == 1

    override fun toString(): String = "LoginStatus(" +
            "status:$status" +
            ",statusVerbose:$statusVerbose" +
            "${if (userId == null) "" else ",userId:$userId"}" +
            "${if (userName == null) "" else ",userName:$userName"}" +
            "${if (preferredLanguage == null) "" else ",preferredLanguage:$preferredLanguage"}" +
            "${if (country == null) "" else ",country:$country"}" +
            "${if (isAdmin == null) "" else ",isAdmin:$isAdmin"}" +
            "${if (isModerator == null) "" else ",isModerator:$isModerator"}" +
            "${if (cookie == null) "" else ",cookie:$cookie"}" +
            ")"
}