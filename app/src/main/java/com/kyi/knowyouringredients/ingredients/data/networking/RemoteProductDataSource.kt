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

    override suspend fun getProducts(
        brands: String?,
        categories: String?,
        nutritionGrade: String?,
        page: Int,
        pageSize: Int
    ): Result<Pair<List<Product>, Int>, NetworkError> {
        return safeCall<ProductsResponseDto>(
            urlProvider = { constructUrl("/search") },
            execute = { url ->
                httpClient.get(url) {
                    parameter(
                        "fields",
                        "code,product_name,brands,packagings,ingredients,nutriments,nutrition_grade_fr,nutrition_score_fr,nova_group,nutrient_levels,additives_n,additives_tags,allergens_tags,categories_tags,quantity,serving_size,image_url,image_small_url,image_thumb_url"
                    )
                    parameter("page", page.toString())
                    parameter("page_size", pageSize.toString())
                    brands?.let { parameter("brands_tags", it) }
                    categories?.let { parameter("categories_tags", it) }
                    nutritionGrade?.let { parameter("nutrition_grades_tags", it) }
                }
            }
        ).map { response ->
            response.products.map { it.toProduct() } to response.count
        }
    }

    override suspend fun fetchProductByBarcode(
        barcode: String,
        fields: String,
        productType: String
    ): Result<Product, NetworkError> {
        return safeCall<ProductResponseDto>(
            urlProvider = { constructUrl("product/$barcode") },
            execute = { url ->
                httpClient.get(url) {
                    parameter("fields", fields)
                    parameter("product_type", productType)
                }
            }
        ).map { response ->
            response.product.toProduct()
        }
    }
}