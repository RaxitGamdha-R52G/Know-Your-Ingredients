package com.kyi.knowyouringredients.ingredients.data.mappers

import com.kyi.knowyouringredients.ingredients.data.networking.dto.ProductDto
import com.kyi.knowyouringredients.ingredients.domain.models.AdditivesInfo
import com.kyi.knowyouringredients.ingredients.domain.models.AllergensInfo
import com.kyi.knowyouringredients.ingredients.domain.models.CategoriesInfo
import com.kyi.knowyouringredients.ingredients.domain.models.NutritionInfo
import com.kyi.knowyouringredients.ingredients.domain.models.Product

fun ProductDto.toProduct(): Product {
    return Product(
        code = code,
        productName = productName,
        brands = brands,
        packaging = packaging,
        ingredients = ingredients,
        nutriments = nutriments,
        nutritionInfo = NutritionInfo(
            grade = nutritionGrade,
            score = nutritionScore,
            nutrientLevels = nutrientLevels,
            novaGroup = novaGroup
        ).takeIf { it.grade != null || it.score != null || it.nutrientLevels != null || it.novaGroup != null },
        additivesInfo = AdditivesInfo(
            count = additivesN,
            tags = additivesTags
        ).takeIf { it.count != null || it.tags.isNotEmpty() },
        allergensInfo = AllergensInfo(
            tags = allergensTags
        ).takeIf { it.tags.isNotEmpty() },
        categoriesInfo = CategoriesInfo(
            tags = categoriesTags
        ).takeIf { it.tags.isNotEmpty() },
        quantity = quantity,
        servingSize = servingSize
    )
}