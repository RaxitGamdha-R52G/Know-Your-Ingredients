package com.kyi.knowyouringredients.ingredients.data.networking

import com.kyi.knowyouringredients.core.data.networking.constructUrl
import com.kyi.knowyouringredients.core.data.networking.safeCall
import com.kyi.knowyouringredients.core.domain.util.NetworkError
import com.kyi.knowyouringredients.core.domain.util.Result
import com.kyi.knowyouringredients.core.domain.util.map
import com.kyi.knowyouringredients.ingredients.data.mappers.toProduct
import com.kyi.knowyouringredients.ingredients.data.networking.dto.ProductResponseDto
import com.kyi.knowyouringredients.ingredients.data.networking.dto.ProductsResponseDto
import com.kyi.knowyouringredients.ingredients.domain.ProductDataSource
import com.kyi.knowyouringredients.ingredients.domain.models.Product
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class RemoteProductDataSource(
    private val httpClient: HttpClient
) : ProductDataSource {
    override suspend fun getProducts(): Result<List<Product>, NetworkError> {
        return safeCall<ProductsResponseDto> {
            httpClient.get(
                urlString = constructUrl("/products")
            )
        }.map { response: ProductsResponseDto ->
            response.products.map { it.toProduct() }
        }
    }

    override suspend fun fetchProductByBarcode(
        barcode: String,
        fields: String,
        productType: String,
        countryCode: String,
        languageCode: String,
        tagsLanguageCode: String
    ): Result<Product, NetworkError> {
        return safeCall<ProductResponseDto> {
            httpClient.get(
                constructUrl("product/$barcode")
            ) {
                parameter("fields", fields)
                parameter("product_type", productType)
                parameter("cc", countryCode)
                parameter("lc", languageCode)
                parameter("tags_lc", tagsLanguageCode)
            }
        }.map { response: ProductResponseDto ->
            response.product.toProduct()
        }
    }
}