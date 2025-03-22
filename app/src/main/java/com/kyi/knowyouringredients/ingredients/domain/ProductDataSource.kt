package com.kyi.knowyouringredients.ingredients.domain

import com.kyi.knowyouringredients.core.domain.util.NetworkError
import com.kyi.knowyouringredients.core.domain.util.Result
import com.kyi.knowyouringredients.ingredients.domain.models.Product

interface ProductDataSource {
    suspend fun getProducts(
        brands: String? = null,
        categories: String? = null,
        nutritionGrade: String? = null,
        page: Int = 1,
        pageSize: Int = 24
    ): Result<Pair<List<Product>, Int>, NetworkError>

    suspend fun fetchProductByBarcode(
        barcode: String,
        fields: String,
        productType: String,
        countryCode: String,
        languageCode: String,
        tagsLanguageCode: String
    ): Result<Product, NetworkError>
}