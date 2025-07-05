package com.kyi.knowyouringredients.ingredients.data.mappers

import com.kyi.knowyouringredients.ingredients.data.networking.dto.IngredientDto
import com.kyi.knowyouringredients.ingredients.data.networking.dto.PackagingDto
import com.kyi.knowyouringredients.ingredients.data.networking.dto.ProductDto
import com.kyi.knowyouringredients.ingredients.domain.models.Ingredient
import com.kyi.knowyouringredients.ingredients.domain.models.Packaging
import com.kyi.knowyouringredients.ingredients.domain.models.Product

fun ProductDto.toProduct(): Product {
    return Product(
        code = code ?: "",
        productName = productName ?: "Unknown Product",
        brands = brands,
        quantity = quantity,
        servingSize = servingSize,

        // Ingredients fields
        ingredients = ingredients?.map { it.toIngredient() } ?: emptyList(),

        // Nutriments fields
        energyKcal100g = nutriments?.energyKcal100g,
        fat100g = nutriments?.fat100g,
        saturatedFat100g = nutriments?.saturatedFat100g,
        carbohydrates100g = nutriments?.carbohydrates100g,
        sugars100g = nutriments?.sugars100g,
        proteins100g = nutriments?.proteins100g,
        salt100g = nutriments?.salt100g,
        energyKcalServing = nutriments?.energyKcalServing,
        fatServing = nutriments?.fatServing,
        saturatedFatServing = nutriments?.saturatedFatServing,
        carbohydratesServing = nutriments?.carbohydratesServing,
        sugarsServing = nutriments?.sugarsServing,
        proteinsServing = nutriments?.proteinsServing,
        saltServing = nutriments?.saltServing,

        // Nutrition Info fields
        nutritionGrade = nutritionGrade,
        nutritionScore = nutritionScore,
        nutrientLevels = nutrientLevels,
        novaGroup = novaGroup,

        // Additives Info fields
        additivesCount = additivesN,
        additivesTags = additivesTags ?: emptyList(),

        // Allergens Info fields
        allergensTags = allergensTags ?: emptyList(),

        // Categories Info fields
        categoriesTags = categoriesTags ?: emptyList(),

        // Packaging fields
        packaging = packagings?.map { it.toPackaging() } ?: emptyList(),

        // Labels Info fields
        labelsTags = labelsTags ?: emptyList(),

        // EcoScore Info fields
        ecoScoreGrade = ecoscoreGrade,
        ecoScoreScore = ecoscoreScore,

        // Image Info fields
        mainImageUrl = imageUrl,
        smallImageUrl = imageFrontUrl,
        frontThumbUrl = imageFrontThumbUrl
    )
}

fun PackagingDto.toPackaging(): Packaging {
    return Packaging(
        material = material,
        numberOfUnits = numberOfUnits ?: "1",
        shape = shape,
        recycling = recycling
    )
}

fun IngredientDto.toIngredient(): Ingredient {
    return Ingredient(
        id = id ?: "",
        name = text ?: "",
        isVegan = vegan,
        isVegetarian = vegetarian,
        percentEstimate = percentEstimate,
        subIngredients = subIngredients?.map { it.toIngredient() } ?: emptyList()
    )
}