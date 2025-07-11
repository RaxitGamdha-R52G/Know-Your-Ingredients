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
        pageSize: Int = 25
    ): Result<Pair<List<Product>, Int>, NetworkError>

    suspend fun fetchProductByBarcode(
        barcode: String,
        productType: String = "all"
    ): Result<Product, NetworkError>


}