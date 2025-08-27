package com.kyi.openfoodfactsapi.models


class SignUpStatus(status: Status) : Status by status {

    val statusErrors: Iterable<SignUpStatusError>? = null // Implement logic

    companion object {

        const val WRONG_USER_OR_PASSWORD_ERROR_MESSAGE = "Incorrect user name or password"

        const val OPEN_NEW_ISSUE_URL = "https://github.com/openfoodfacts/openfoodfacts-dart/issues/new"

        const val SERVER_ERROR_IN_ENGLISH = "No response, open an issue here: $OPEN_NEW_ISSUE_URL"

        const val SERVER_ERROR_STATUS = 500

        const val STATUS_OK = "status ok"

        fun fromStatus(status: Status): SignUpStatus {
            // Implementation based on Dart logic
            return SignUpStatus(status)
        }
    }

    enum class SignUpStatusError {
        INCORRECT_EMAIL,
        EMAIL_ALREADY_USED,
        USERNAME_ALREADY_USED,
        INVALID_USERNAME,
        INVALID_PASSWORD,
        SERVER_BUSY,
        UNKNOWN
    }
}