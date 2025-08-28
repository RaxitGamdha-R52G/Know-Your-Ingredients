package com.kyi.knowyouringredients.ingredients.domain.repository

import com.kyi.knowyouringredients.core.domain.util.Result
import com.kyi.knowyouringredients.ingredients.domain.models.HistoryItem
import com.kyi.knowyouringredients.core.domain.util.Error
import kotlinx.coroutines.flow.Flow

interface HistoryDataSource {
    suspend fun addHistoryItem(userId: String, item: HistoryItem): Result<Unit, Error>
    fun getHistory(userId: String, type: String): Flow<Result<List<HistoryItem>, Error>>

    suspend fun deleteHistoryItem(userId: String, item: HistoryItem): Result<Unit, Error>
}