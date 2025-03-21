package com.kyi.knowyouringredients.ingredients.data.mappers

import com.kyi.knowyouringredients.ingredients.data.networking.dto.ProductDto
import com.kyi.knowyouringredients.ingredients.domain.models.Product

fun ProductDto.toProduct(): Product {
    return Product(
        code = code,
        productName = productName,
        brands = brands,
        packaging = packaging,
        ingredients = ingredients,
        nutriments = nutriments,
        nutritionGrade = nutritionGrade,
        nutritionScore = nutritionScore,
        additivesN = additivesN,
        additivesTags = additivesTags,
        allergensTags = allergensTags,
        categoriesTags = categoriesTags,
        quantity = quantity,
        servingSize = servingSize
    )
}