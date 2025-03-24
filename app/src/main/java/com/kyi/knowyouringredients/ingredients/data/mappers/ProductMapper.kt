package com.kyi.knowyouringredients.ingredients.data.mappers

import com.kyi.knowyouringredients.ingredients.data.networking.dto.IngredientDto
import com.kyi.knowyouringredients.ingredients.data.networking.dto.NutrimentsDto
import com.kyi.knowyouringredients.ingredients.data.networking.dto.PackagingDto
import com.kyi.knowyouringredients.ingredients.data.networking.dto.ProductDto
import com.kyi.knowyouringredients.ingredients.domain.models.AdditivesInfo
import com.kyi.knowyouringredients.ingredients.domain.models.AllergensInfo
import com.kyi.knowyouringredients.ingredients.domain.models.CategoriesInfo
import com.kyi.knowyouringredients.ingredients.domain.models.EcoScoreInfo
import com.kyi.knowyouringredients.ingredients.domain.models.ImageInfo
import com.kyi.knowyouringredients.ingredients.domain.models.Ingredient
import com.kyi.knowyouringredients.ingredients.domain.models.LabelsInfo
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
        servingSize = servingSize,
        labelsInfo = LabelsInfo(
            tags = labelsTags ?: emptyList()
        ).takeIf { it.tags.isNotEmpty() },
        ecoScoreInfo = EcoScoreInfo(
            grade = ecoscoreGrade,
            score = ecoscoreScore
        ).takeIf { it.grade != null || it.score != null },
        imageInfo = ImageInfo(
            mainImageUrl = imageUrl,
            frontImageUrl = imageFrontUrl,
            frontThumbUrl = imageFrontThumbUrl
        ).takeIf { it.mainImageUrl != null || it.frontImageUrl != null || it.frontThumbUrl != null }
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
        saltServing = saltServing
    )
}

//fun EcoScoreInfoDto.toEcoScoreInfo(): EcoScoreInfo {
//    return EcoScoreInfo(
//        grade = grade,
//        score = score
//    )
//}
//
//fun ImageInfoDto.toImageInfo(): ImageInfo {
//    return ImageInfo(
//        mainImageUrl = mainImageUrl,
//        frontImageUrl = frontImageUrl,
//        frontThumbUrl = frontThumbUrl
//    )
//}