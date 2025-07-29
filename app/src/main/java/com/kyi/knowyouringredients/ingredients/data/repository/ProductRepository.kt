package com.kyi.knowyouringredients.ingredients.data.repository

import com.kyi.knowyouringredients.core.domain.util.NetworkError
import com.kyi.knowyouringredients.core.domain.util.Result
import com.kyi.knowyouringredients.ingredients.data.networking.RemoteProductDataSource
import com.kyi.knowyouringredients.ingredients.domain.models.Product
import com.kyi.knowyouringredients.ingredients.domain.repository.ProductDataSource

class ProductRepository(
    private val remoteDataSource: RemoteProductDataSource,
    private val localDataSource: ProductDataSource? = null, // Nullable for future local source
) : ProductDataSource {

    override suspend fun getProducts(
        searchTerm: String,
        page: Int,
        pageSize: Int,
        productType: String
    ): Result<Pair<List<Product>, Int>, NetworkError> {
        // For now, delegate directly to remote; add local logic later
        return remoteDataSource.getProducts(searchTerm, page, pageSize, productType)
    }

    override suspend fun fetchProductByBarcode(
        barcode: String,
        productType: String
    ): Result<Product, NetworkError> {
        // For now, delegate directly to remote; add local logic later
        return remoteDataSource.fetchProductByBarcode(barcode, productType)

    }

}