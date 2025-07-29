package com.kyi.knowyouringredients.ingredients.domain.repository

import com.kyi.knowyouringredients.core.domain.util.NetworkError
import com.kyi.knowyouringredients.core.domain.util.Result
import com.kyi.knowyouringredients.ingredients.domain.models.Product

interface ProductDataSource {
    suspend fun getProducts(
        searchTerm: String,
        page: Int = 1,
        pageSize: Int = 25,
        productType: String = "food"
    ): Result<Pair<List<Product>, Int>, NetworkError>

    suspend fun fetchProductByBarcode(
        barcode: String,
        productType: String = "all"
    ): Result<Product, NetworkError>


}