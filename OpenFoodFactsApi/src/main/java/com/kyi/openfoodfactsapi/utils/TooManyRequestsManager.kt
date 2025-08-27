package com.kyi.openfoodfactsapi.utils


class TooManyRequestsManager(
    val maxCount: Int,
    val duration: Duration
) {

    private val requestTimestamps = mutableListOf<Int>()

    suspend fun waitIfNeeded() {
        while (requestTimestamps.size >= maxCount) {
            val previousInMillis = requestTimestamps.first()
            val nowInMillis = DateTime.now().millisecondsSinceEpoch
            val waitingInMillis = duration.inMilliseconds - nowInMillis + previousInMillis
            if (waitingInMillis > 0) {
                delay(waitingInMillis.toLong())
            }
            requestTimestamps.removeAt(0)
        }
        val now = DateTime.now()
        val nowInMillis = now.millisecondsSinceEpoch
        requestTimestamps.add(nowInMillis)
    }
}

val searchProductsTooManyRequestsManager = TooManyRequestsManager(
    maxCount = 10,
    duration = Duration(minutes = 1)
)

val getProductTooManyRequestsManager = TooManyRequestsManager(
    maxCount = 100,
    duration = Duration(minutes = 1)
)