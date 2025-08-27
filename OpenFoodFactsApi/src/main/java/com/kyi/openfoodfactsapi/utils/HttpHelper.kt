package com.kyi.openfoodfactsapi.utils

import http.Response
import http.MultipartRequest
import http.StreamedResponse
import dart.async.Completer
import dart.convert.json
import dart.convert.utf8
import dart.math.base64Encode
import com.kyi.openfoodfactsapi.models.Status
import com.kyi.openfoodfactsapi.models.User
import path.basename

class HttpHelper private constructor() {

    companion object {

        val instance: HttpHelper get() = _instance ?: HttpHelper().also { _instance = it }

        private var _instance: HttpHelper? = null

        val FROM = "anonymous"

        fun addUserAgentParameters(map: MutableMap<String, Any?>?): MutableMap<String, Any?> {
            if (OpenFoodAPIConfiguration.userAgent == null) throw Exception("A User-Agent must be set before calling this method")
            if(map == null) return mutableMapOf<String, Any?>()
            //map ?: mutableMapOf<String, Any?>()
            map["app_name"] = OpenFoodAPIConfiguration.userAgent!!.name
            OpenFoodAPIConfiguration.userAgent!!.version?.let { map["app_version"] = it }
            OpenFoodAPIConfiguration.uuid?.let { map["app_uuid"] = it }
            OpenFoodAPIConfiguration.userAgent!!.system?.let { map["app_platform"] = it }
            OpenFoodAPIConfiguration.userAgent!!.comment?.let { map["comment"] = it }
            return map
        }

        fun jsonDecode(string: String): Any? {
            try {
                return json.decode(string)
            } catch (e: Exception) {
                if (string.startsWith("<html>")) {
                    throw Exception("JSON expected, html found: ${string.split("\n")[1]}")
                }
                if (string.startsWith("<h1>Software error:</h1>")) {
                    throw Exception("JSON expected, software error found: ${string.split("\n")[1]}")
                }
                if (string.startsWith("<!DOCTYPE html>")) {
                    val titleOpen = "<title>"
                    val titleClose = "</title>"
                    var pos1 = string.indexOf(titleOpen)
                    if (pos1 >= 0) {
                        pos1 += titleOpen.length
                        val pos2 = string.indexOf(titleClose)
                        if (pos2 >= 0 && pos1 < pos2) {
                            throw Exception("JSON expected, server error found: ${string.substring(pos1, pos2)}")
                        }
                    }
                }
                throw e
            }
        }

        fun jsonDecodeUtf8(response: Response): Any? = jsonDecode(utf8.decode(response.bodyBytes))

        private fun isIso88591(input: String): Boolean {
            try {
                latin1.encode(input)
                return true
            } catch (e: Exception) {
                return false
            }
        }

        private fun getSafeString(input: String): String {
            if (isIso88591(input)) return input
            return base64Encode(utf8.encode(input))
        }

        fun imagineMediaType(filename: String): MediaType? {
            var ext = extension(filename)
            if (ext.isEmpty()) return null
            ext = ext.substring(1).lowercase()
            return when (ext) {
                "jpg", "jpeg" -> MediaType("image", "jpeg")
                "png" -> MediaType("image", "png")
                "webp" -> MediaType("image", "webp")
                else -> null
            }
        }
    }

    suspend fun doGetRequest(
        uri: Uri,
        user: User? = null,
        uriHelper: UriHelper,
        bearerToken: String? = null,
        addCookiesToHeader: Boolean = false
    ): Response {
        val headers = buildHeaders(user = user, uriHelper = uriHelper, addCredentialsToHeader = false, bearerToken = bearerToken, addCookieToHeader = addCookiesToHeader)
        return http.get(uri, headers = headers)
    }

    suspend fun doPostRequest(
        uri: Uri,
        body: Map<String, String>,
        user: User?,
        uriHelper: UriHelper,
        addCredentialsToBody: Boolean,
        addCredentialsToHeader: Boolean = false
    ): Response {
        if (addCredentialsToBody) {
            user?.let { body.putAll(it.toData()) }
        }
        val headers = buildHeaders(user = user, uriHelper = uriHelper, addCredentialsToHeader = addCredentialsToHeader)
        return http.post(uri, headers = headers, body = addUserAgentParameters(body))
    }

    suspend fun doDeleteRequest(
        uri: Uri,
        user: User?,
        uriHelper: UriHelper,
        bearerToken: String? = null
    ): Response {
        val headers = buildHeaders(user = user, uriHelper = uriHelper, addCredentialsToHeader = false, bearerToken = bearerToken)
        return http.delete(uri, headers = headers)
    }

    suspend fun doPutRequest(
        uri: Uri,
        jsonBody: String,
        user: User?,
        uriHelper: UriHelper,
        bearerToken: String? = null
    ): Response {
        val headers = buildHeaders(user = user, uriHelper = uriHelper, addCredentialsToHeader = false, bearerToken = bearerToken)
        return http.put(uri, headers = headers, body = jsonBody)
    }

    suspend fun doPostJsonRequest(uri: Uri, jsonBody: String, uriHelper: UriHelper, bearerToken: String): Response {
        val headers = getBearerHeader(bearerToken)
        return http.post(uri, headers = headers, body = jsonBody)
    }

    val userInfoForTest = "off:off"

    suspend fun doPatchRequest(uri: Uri, body: Map<String, Any?>, user: User?, uriHelper: UriHelper, bearerToken: String? = null, addUserAgentParameters: Boolean = true): Response {
        val headers = buildHeaders(user = user, uriHelper = uriHelper, addCredentialsToHeader = false, bearerToken = bearerToken)
        return http.patch(uri, headers = headers, body = jsonEncode(if (addUserAgentParameters) addUserAgentParameters(body) else body))
    }

    suspend fun doMultipartRequest(uri: Uri, body: Map<String, String>, files: Map<String, Uri>? = null, user: User?, uriHelper: UriHelper): Status {
        val request = MultipartRequest("POST", uri)
        request.headers.putAll(buildHeaders(user = user, uriHelper = uriHelper, addCredentialsToHeader = false) as Map<String, String>)
        request.headers["Content-Type"] = "multipart/form-data"
        addUserAgentParameters(body)
        request.fields.putAll(body)
        user?.let { request.fields.putAll(it.toData()) }

        files?.forEach { (key, value) ->
            val fileBytes = UriReader.instance.readAsBytes(value)
            val multipartFile = MultipartFile.fromBytes(key, fileBytes, filename = basename(value.toString()))
            request.files.add(multipartFile)
        }

        val response = request.send()

        if (response.statusCode == 200) {
            val responseBody = extractResponseAsString(response)
            return try {
                Status.fromJson(jsonDecode(responseBody))
            } catch (e: Exception) {
                Status(status = 200, body = responseBody)
            }
        } else {
            return Status(status = response.statusCode, error = response.reasonPhrase)
        }
    }

    suspend fun extractResponseAsString(response: StreamedResponse): String {
        val completer = Completer<String>()
        val contents = StringBuffer()
        response.stream.transform(utf8.decoder).listen({ contents.write(it) }, onDone = { completer.complete(contents.toString()) })
        return completer.future
    }

    private fun getBearerHeader(bearerToken: String): Map<String, String> = mapOf(
        "Authorization" to "bearer $bearerToken",
        "Content-Type" to "application/json"
    )

    private fun buildHeaders(user: User?, uriHelper: UriHelper, addCredentialsToHeader: Boolean, bearerToken: String? = null, addCookieToHeader: Boolean = false): Map<String, String>? {
        bearerToken?.let { return getBearerHeader(it) }

        val headers = mutableMapOf<String, String>()

        if (OpenFoodAPIConfiguration.userAgent == null) throw Exception("A User-Agent must be set before calling this method")

        headers["Accept"] = "application/json"
        if (!UriReader.instance.isWeb) {
            headers["User-Agent"] = OpenFoodAPIConfiguration.userAgent!!.toValueString()
        }
        headers["From"] = getSafeString(OpenFoodAPIConfiguration.getUser(user)?.userId ?: FROM)

        val isTestModeActive = uriHelper.isTestMode
        if (isTestModeActive && !addCredentialsToHeader) {
            val token = "Basic ${base64Encode(utf8.encode(userInfoForTest))}"
            headers["Authorization"] = token
        }

        if (addCredentialsToHeader) {
            OpenFoodAPIConfiguration.getUser(user)?.let { myUser ->
                val userId = myUser.userId
                val password = myUser.password
                val token = "Basic ${base64Encode(utf8.encode("$userId:$password"))}"
                headers["Authorization"] = token
            }
        }
        if (addCookieToHeader && user?.cookie != null) {
            headers["Cookie"] = user.cookie!!
        }

        return headers
    }

    private fun getSafeString(input: String): String {
        if (isIso88591(input)) return input
        return base64Encode(utf8.encode(input))
    }

    private fun isIso88591(input: String): Boolean {
        try {
            latin1.encode(input)
            return true
        } catch (e: Exception) {
            return false
        }
    }
}