package com.kyi.openfoodfactsapi.utils

import android.net.Uri

open class UriHelper(
    val host: String,
    val scheme: String = "https",
    val isTestMode: Boolean = false,
    val defaultAddUserAgentParameters: Boolean = false
) {

    fun getUri(
        path: String,
        queryParameters: Map<String, Any?>? = null,
        addUserAgentParameters: Boolean? = null,
        userInfo: String? = null,
        forcedHost: String? = null
    ): Uri {
        val builder = Uri.Builder()
        builder.scheme(scheme)
        builder.path(path)
        builder.encodedAuthority((forcedHost ?: host).let { h ->
            if (userInfo != null) "$userInfo@$h" else h
        })

        val finalQueryParameters = if (addUserAgentParameters ?: defaultAddUserAgentParameters) {
            HttpHelper.addUserAgentParameters(queryParameters as MutableMap<String, Any?>?)
        } else {
            queryParameters
        }

        finalQueryParameters?.forEach { (key, value) ->
            builder.appendQueryParameter(key, value?.toString())
        }


        return builder.build()
    }
//    ): Uri = Uri(
//        scheme = scheme,
//        host = forcedHost ?: host,
//        path = path,
//        queryParameters = if (addUserAgentParameters ?: defaultAddUserAgentParameters) HttpHelper.addUserAgentParameters(queryParameters as MutableMap<String, Any?>?) else queryParameters,
//        userInfo = userInfo
//    )

    fun getPostUri(path: String): Uri = getUri(path = path, addUserAgentParameters = false)

    fun replaceSubdomain(
        uri: Uri,
        language: OpenFoodFactsLanguage? = null,
        country: OpenFoodFactsCountry? = null
    ): Uri = replaceSubdomainWithCodes(
        uri,
        languageCode = language?.code ?: (OpenFoodAPIConfiguration.globalLanguages?.takeIf { it.isNotEmpty() }?.get(0)?.code),
        countryCode = country?.offTag ?: OpenFoodAPIConfiguration.globalCountry?.offTag
    )

    fun replaceSubdomainWithCodes(
        uri: Uri,
        languageCode: String? = null,
        countryCode: String? = null
    ): Uri {
        val currentHost = uri.host ?: return uri
        val initialSubdomain = currentHost.split(".")[0]
        val subdomain = if (languageCode != null) "$countryCode-$languageCode" else countryCode ?: initialSubdomain
        val newHost = currentHost.replaceFirst("$initialSubdomain.", "$subdomain.")

        return uri.buildUpon()
            .authority(newHost)
            .build()
    }
//    ): Uri {
//        val initialSubdomain = uri.host.split(".")[0]
//        val subdomain = if (languageCode != null) "$countryCode-$languageCode" else countryCode ?: initialSubdomain
//        return uri.copy(host = uri.host.replaceFirst("$initialSubdomain.", "$subdomain."))
//    }
}

class UriProductHelper(
    val domain: String,
    scheme: String = "https",
    isTestMode: Boolean = false,
    val userInfoForPatch: String? = null,
    defaultAddUserAgentParameters: Boolean = true
) : UriHelper(host = "world.$domain", scheme = scheme, isTestMode = isTestMode, defaultAddUserAgentParameters = defaultAddUserAgentParameters) {

    fun getImageUrlBase(): String = "$scheme://images.$domain/images/products"

    fun getHost(subdomain: String): String = "$subdomain.$domain"

    fun getPatchUri(path: String): Uri = getUri(path = path, addUserAgentParameters = false, userInfo = userInfoForPatch)

    fun getProductImageRootUrl(barcode: String): String = "${getImageUrlBase()}/${getBarcodeSubPath(barcode)}"

    companion object {

        fun getBarcodeSubPath(barcode: String): String {
            val typicalLength = 13
            val length = barcode.length
            val workBarcode = if (length >= typicalLength) barcode else "0".repeat(typicalLength - length) + barcode
            val p1 = workBarcode.substring(0, 3)
            val p2 = workBarcode.substring(3, 6)
            val p3 = workBarcode.substring(6, 9)
            val p4 = workBarcode.substring(9)
            return "$p1/$p2/$p3/$p4"
        }
    }
}