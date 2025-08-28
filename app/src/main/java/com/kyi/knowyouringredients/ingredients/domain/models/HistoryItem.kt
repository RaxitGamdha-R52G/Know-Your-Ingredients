package com.kyi.knowyouringredients.ingredients.domain.models

import com.google.firebase.Timestamp

data class HistoryItem(
    val name: String = "",
    val code: String = "",
    val imageUrl: String = "",
    val type: String = "", // "scanned" or "searched"
    val timestamp: Timestamp = Timestamp.now()
)