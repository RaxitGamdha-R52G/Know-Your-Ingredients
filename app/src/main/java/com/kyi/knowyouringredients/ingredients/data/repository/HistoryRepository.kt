package com.kyi.knowyouringredients.ingredients.data.repository

import com.kyi.knowyouringredients.core.domain.util.Result
import com.kyi.knowyouringredients.ingredients.data.history.FirestoreHistoryDataSource
import com.kyi.knowyouringredients.ingredients.domain.models.HistoryItem
import com.kyi.knowyouringredients.ingredients.domain.repository.HistoryDataSource
import com.kyi.knowyouringredients.core.domain.util.Error
import kotlinx.coroutines.flow.Flow

class HistoryRepository(
    private val firestoreDataSource: FirestoreHistoryDataSource
) : HistoryDataSource {
    override suspend fun addHistoryItem(userId: String, item: HistoryItem): Result<Unit, Error> {
        return firestoreDataSource.addHistoryItem(userId, item)
    }

    override fun getHistory(userId: String, type: String): Flow<Result<List<HistoryItem>, Error>> {
        return firestoreDataSource.getHistory(userId, type)
    }

    override suspend fun deleteHistoryItem(userId: String, item: HistoryItem): Result<Unit, Error> {
        return firestoreDataSource.deleteHistoryItem(userId, item)
    }

}