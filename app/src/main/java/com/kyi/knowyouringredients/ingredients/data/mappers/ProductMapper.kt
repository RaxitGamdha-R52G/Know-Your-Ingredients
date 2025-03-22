package com.kyi.knowyouringredients.ingredients.data.mappers

import com.kyi.knowyouringredients.ingredients.data.networking.dto.IngredientDto
import com.kyi.knowyouringredients.ingredients.data.networking.dto.NutrimentsDto
import com.kyi.knowyouringredients.ingredients.data.networking.dto.PackagingDto
import com.kyi.knowyouringredients.ingredients.data.networking.dto.ProductDto
import com.kyi.knowyouringredients.ingredients.domain.models.AdditivesInfo
import com.kyi.knowyouringredients.ingredients.domain.models.AllergensInfo
import com.kyi.knowyouringredients.ingredients.domain.models.CategoriesInfo
import com.kyi.knowyouringredients.ingredients.domain.models.Ingredient
import com.kyi.knowyouringredients.ingredients.domain.models.Nutriments
import com.kyi.knowyouringredients.ingredients.domain.models.NutritionInfo
import com.kyi.knowyouringredients.ingredients.domain.models.Packaging
import com.kyi.knowyouringredients.ingredients.domain.models.Product

fun ProductDto.toProduct(): Product {
    return Product(
        code = code ?: "",
        productName = productName ?: "Unknown Product",
        brands = brands,
        packaging = packagings?.map { it.toPackaging() } ?: emptyList(),
        ingredients = ingredients?.map { it.toIngredient() } ?: emptyList(),
        nutriments = nutriments?.toNutriments(),
        nutritionInfo = NutritionInfo(
            grade = nutritionGrade,
            score = nutritionScore,
            nutrientLevels = nutrientLevels,
            novaGroup = novaGroup
        ).takeIf { it.grade != null || it.score != null || it.nutrientLevels != null || it.novaGroup != null },
        additivesInfo = AdditivesInfo(
            count = additivesN,
            tags = additivesTags ?: emptyList()
        ).takeIf { it.count != null || it.tags.isNotEmpty() },
        allergensInfo = AllergensInfo(
            tags = allergensTags ?: emptyList()
        ).takeIf { it.tags.isNotEmpty() },
        categoriesInfo = CategoriesInfo(
            tags = categoriesTags ?: emptyList()
        ).takeIf { it.tags.isNotEmpty() },
        quantity = quantity,
        servingSize = servingSize
    )
}

fun PackagingDto.toPackaging(): Packaging {
    val (quantityValue, quantityUnit) = quantityPerUnit?.let {
        val parts = it.split(" ", limit = 2)
        if (parts.size == 2) parts[0] to parts[1] else it to "g"
    } ?: ("0" to "g")
    return Packaging(
        material = material,
        numberOfUnits = numberOfUnits ?: "1",
        quantityValue = quantityValue,
        quantityUnit = quantityUnit,
        shape = shape,
        recycling = recycling,
        weightMeasured = weightMeasured
    )
}

fun IngredientDto.toIngredient(): Ingredient {
    return Ingredient(
        id = id ?: "",
        name = text ?: "",
        isVegan = vegan,
        isVegetarian = vegetarian,
        percent = percent,
        percentEstimate = percentEstimate,
        percentMin = percentMin,
        percentMax = percentMax,
        ciqualFoodCode = ciqualFoodCode,
        ecobalyseCode = null, // Not in API response
        fromPalmOil = fromPalmOil,
        isInTaxonomy = null, // Not in API response
        subIngredients = subIngredients?.map { it.toIngredient() } ?: emptyList()
    )
}

fun NutrimentsDto.toNutriments(): Nutriments {
    return Nutriments(
        energyKcal100g = energyKcal100g,
        fat100g = fat100g,
        saturatedFat100g = saturatedFat100g,
        carbohydrates100g = carbohydrates100g,
        sugars100g = sugars100g,
        proteins100g = proteins100g,
        salt100g = salt100g,
        energyKcalServing = energyKcalServing,
        fatServing = fatServing,
        saturatedFatServing = saturatedFatServing,
        carbohydratesServing = carbohydratesServing,
        sugarsServing = sugarsServing,
        proteinsServing = proteinsServing,
        saltServing = saltServing,
        energyKcalUnit = energyKcalUnit,
        fatUnit = fatUnit,
        carbohydratesUnit = carbohydratesUnit,
        sugarsUnit = sugarsUnit,
        proteinsUnit = proteinsUnit,
        saltUnit = saltUnit,
        nutritionScoreFr100g = nutritionScoreFr100g,
        carbonFootprint100g = carbonFootprint100g,
        fruitsVeggiesNuts100g = fruitsVeggiesNuts100g
    )
}