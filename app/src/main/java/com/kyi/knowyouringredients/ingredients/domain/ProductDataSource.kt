package com.kyi.knowyouringredients.ingredients.domain

import com.kyi.knowyouringredients.core.domain.util.NetworkError
import com.kyi.knowyouringredients.core.domain.util.Result
import com.kyi.knowyouringredients.ingredients.domain.models.Product

interface ProductDataSource {
    suspend fun getProducts(): Result<List<Product>, NetworkError>
    suspend fun fetchProductByBarcode(
        barcode: String,
        fields: String = "raw",             // Optional: Comma-separated fields to retrieve
        productType: String = "all",        // Optional: Product type (e.g., "food", "all", "beauty")
        countryCode: String = "in",         // Optional: 2-letter country code (e.g., "us")
        languageCode: String = "en",        // Optional: 2-letter language code (e.g., "fr")
        tagsLanguageCode: String = "en"     // Optional: Language for tag names (e.g., "en")
    ): Result<Product, NetworkError>
}