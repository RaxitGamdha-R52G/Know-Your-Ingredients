package com.kyi.knowyouringredients.ingredients.data.history

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.snapshots
import com.google.firebase.firestore.toObjects
import com.kyi.knowyouringredients.core.domain.util.Result
import com.kyi.knowyouringredients.ingredients.domain.models.HistoryItem
import com.kyi.knowyouringredients.core.domain.util.Error
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class FirestoreHistoryDataSource(private val firestore: FirebaseFirestore) {

    suspend fun addHistoryItem(userId: String, item: HistoryItem): Result<Unit, Error> {
        return try {
            firestore.collection("users").document(userId)
                .collection("history").document(item.code) // Use barcode as ID to prevent duplicates
                .set(item)
                .await()
            Result.Success(Unit)
        } catch (e: Exception) {
            // Define a custom error type if needed
            Result.Error(object : Error {})
        }
    }

    suspend fun deleteHistoryItem(userId: String, item: HistoryItem): Result<Unit, Error> {
        return try {
            firestore.collection("users").document(userId)
                .collection("history").document(item.code)
                .delete()
                .await()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(object : Error {})
        }
    }

    fun getHistory(userId: String, type: String): Flow<Result<List<HistoryItem>, Error>> {
        val query = firestore.collection("users").document(userId)
            .collection("history")
            .whereEqualTo("type", type)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .limit(50)

        return query.snapshots().map { snapshot ->
            try {
                Result.Success(snapshot.toObjects<HistoryItem>())
            } catch (e: Exception) {
                Result.Error(object : Error {})
            }
        }
    }
}