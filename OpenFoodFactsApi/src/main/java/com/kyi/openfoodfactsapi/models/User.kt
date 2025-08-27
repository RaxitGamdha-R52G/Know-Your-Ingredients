package com.kyi.openfoodfactsapi.models

import com.kyi.openfoodfactsapi.sources.JsonObject


class User(
    val comment: String? = null,
    val userId: String,
    val password: String,
    val cookie: String? = null
) : JsonObject() {

    override fun toJson(): Map<String, Any?> = mapOfNotNull(
        "comment" to comment,
        "user_id" to userId,
        "password" to password,
        "cookie" to cookie
    )

    companion object {

        fun fromJson(json: Map<String, Any?>): User = User(
            comment = json["comment"] as String?,
            userId = json["user_id"] as String,
            password = json["password"] as String,
            cookie = json["cookie"] as String?
        )

        fun getUserWikiTag(userId: String): String =
            "${userId.substring(0, 1).uppercase()}${userId.substring(1)}"

        fun getUserWikiPage(userId: String): String = "https://wiki.openfoodfacts.org/User:${getUserWikiTag(userId)}"

        fun getUserWikiDiscussionPage(userId: String): String = "https://wiki.openfoodfacts.org/index.php?title=User_talk:${getUserWikiTag(userId)}&action=edit&section=new"
    }
}