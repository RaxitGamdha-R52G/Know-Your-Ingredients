package com.kyi.openfoodfactsapi.models

import com.kyi.openfoodfactsapi.sources.JsonObject


class Status(
    val status: Any? = null,
    val statusVerbose: String? = null,
    val body: String? = null,
    val error: String? = null,
    val imageId: Int? = null
) : JsonObject() {

    fun copyWith(status: Any?, statusVerbose: String?, body: String?, error: String?, imageId: Int?): Status = Status(
        status = status ?: this.status,
        statusVerbose = statusVerbose ?: this.statusVerbose,
        body = body ?: this.body,
        error = error ?: this.error,
        imageId = imageId ?: this.imageId
    )

    companion object {

        const val WRONG_USER_OR_PASSWORD_ERROR_MESSAGE = "Incorrect user name or password"

        const val OPEN_NEW_ISSUE_URL = "https://github.com/openfoodfacts/openfoodfacts-dart/issues/new"

        const val SERVER_ERROR_IN_ENGLISH = "No response, open an issue here: $OPEN_NEW_ISSUE_URL"

        const val SERVER_ERROR_STATUS = 500

        const val STATUS_OK = "status ok"

        fun fromApiResponse(responseBody: String): Status {
            try {
                return fromJson(HttpHelper().jsonDecode(responseBody))
            } catch (e: Exception) {
                return Status(
                    body = responseBody,
                    status = 400,
                    statusVerbose = createStatusVerbose(responseBody, e),
                )
            }
        }

        private fun createStatusVerbose(responseBody: String, exception: Any): String {
            var statusVerbose: String
            if (responseBody.contains(WRONG_USER_OR_PASSWORD_ERROR_MESSAGE)) {
                statusVerbose = WRONG_USER_OR_PASSWORD_ERROR_MESSAGE
            } else {
                statusVerbose = exception.toString()
            }
            return statusVerbose
        }

        fun fromJson(json: Map<String, Any?>): Status = Status(
            status = json["status"],
            statusVerbose = json["status_verbose"] as String?,
            body = json["body"] as String?,
            error = json["error"] as String?,
            imageId = parseInt(json["imgid"])
        )
    }

    override fun toJson(): Map<String, Any?> = mapOf(
        "status" to status,
        "status_verbose" to statusVerbose,
        "body" to body,
        "error" to error,
        "imgid" to imageId
    )

    fun isWrongUsernameOrPassword(): Boolean = statusVerbose == WRONG_USER_OR_PASSWORD_ERROR_MESSAGE

    fun shouldOpenNewIssue(): Boolean = status == SERVER_ERROR_STATUS
}