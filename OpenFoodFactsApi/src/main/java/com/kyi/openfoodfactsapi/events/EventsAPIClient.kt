package com.kyi.openfoodfactsapi.events

import Response
import com.kyi.openfoodfactsapi.models.BadgeBase
import com.kyi.openfoodfactsapi.models.EventsBase
import com.kyi.openfoodfactsapi.models.LeaderboardEntry
import com.kyi.openfoodfactsapi.utils.HttpHelper
import com.kyi.openfoodfactsapi.utils.UriHelper
import com.kyi.openfoodfactsapi.utils.uriHelperEventsProd

object EventsAPIClient {

    suspend fun getEvents(
        userId: String? = null,
        deviceId: String? = null,
        skip: Int? = null,
        limit: Int? = null,
        uriHelper: UriHelper = uriHelperEventsProd
    ): List<EventsBase> {
        val parameters = mutableMapOf<String, String>()
        userId?.let { parameters["user_id"] = it }
        deviceId?.let { parameters["device_id"] = it }
        skip?.let { parameters["skip"] = it.toString() }
        limit?.let { parameters["limit"] = it.toString() }
        val response = HttpHelper.get.doGetRequest(
            uriHelper.getUri(
                path = "/events",
                queryParameters = parameters
            ),
            uriHelper = uriHelper
        )
        checkResponse(response)
        val result = mutableListOf<EventsBase>()
        val json = HttpHelper.get.jsonDecode(response.body) as List<*>
        for (element in json) {
            result.add(EventsBase.fromJson(element as Map<String, Any?>))
        }
        return result
    }

    suspend fun getEventsCount(
        userId: String? = null,
        deviceId: String? = null,
        uriHelper: UriHelper = uriHelperEventsProd
    ): Map<String, Int> {
        val parameters = mutableMapOf<String, String>()
        userId?.let { parameters["user_id"] = it }
        deviceId?.let { parameters["device_id"] = it }
        val response = HttpHelper.get.doGetRequest(
            uriHelper.getUri(
                path = "/events/count",
                queryParameters = parameters
            ),
            uriHelper = uriHelper
        )
        checkResponse(response)
        val result = mutableMapOf<String, Int>()
        val json = HttpHelper.get.jsonDecode(response.body) as Map<String, Any?>
        for ((key, value) in json) {
            result[key] = value as Int
        }
        return result
    }

    suspend fun getScores(
        userId: String? = null,
        deviceId: String? = null,
        eventType: String? = null,
        uriHelper: UriHelper = uriHelperEventsProd
    ): Int {
        val parameters = mutableMapOf<String, String>()
        userId?.let { parameters["user_id"] = it }
        deviceId?.let { parameters["device_id"] = it }
        eventType?.let { parameters["event_type"] = it }
        val response = HttpHelper.get.doGetRequest(
            uriHelper.getUri(
                path = "/scores",
                queryParameters = parameters
            ),
            uriHelper = uriHelper
        )
        checkResponse(response)
        val json = HttpHelper.get.jsonDecode(response.body) as Map<String, Any?>
        return json["score"] as Int
    }

    suspend fun getBadges(
        userId: String? = null,
        deviceId: String? = null,
        uriHelper: UriHelper = uriHelperEventsProd
    ): List<BadgeBase> {
        val parameters = mutableMapOf<String, String>()
        userId?.let { parameters["user_id"] = it }
        deviceId?.let { parameters["device_id"] = it }
        val response = HttpHelper.get.doGetRequest(
            uriHelper.getUri(
                path = "/badges",
                queryParameters = parameters
            ),
            uriHelper = uriHelper
        )
        checkResponse(response)
        val result = mutableListOf<BadgeBase>()
        val json = HttpHelper.get.jsonDecode(response.body) as List<*>
        for (element in json) {
            result.add(BadgeBase.fromJson(element as Map<String, Any?>))
        }
        return result
    }

    suspend fun getLeaderboard(
        eventType: String? = null,
        uriHelper: UriHelper = uriHelperEventsProd
    ): List<LeaderboardEntry> {
        val parameters = mutableMapOf<String, String>()
        eventType?.let { parameters["event_type"] = it }
        val response = HttpHelper.get.doGetRequest(
            uriHelper.getUri(
                path = "/leaderboard",
                queryParameters = parameters
            ),
            uriHelper = uriHelper
        )
        checkResponse(response)
        val result = mutableListOf<LeaderboardEntry>()
        val json = HttpHelper.get.jsonDecode(response.body) as List<*>
        for (element in json) {
            result.add(LeaderboardEntry.fromJson(element as Map<String, Any?>))
        }
        return result
    }

    private fun checkResponse(response: Response, authorizedStatus: List<Int> = listOf(200)) {
        if (!authorizedStatus.contains(response.statusCode)) {
            throw Exception("Wrong status code: ${response.statusCode}")
        }
    }
}