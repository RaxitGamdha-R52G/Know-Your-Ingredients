package com.kyi.knowyouringredients.ingredients.presentation

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

val productPreview = Product(
    code = "123456789",
    productName = "Nutella",
    brands = "Ferrero",
    quantity = "400g",
    servingSize = "15g",
    ingredients = listOf(
        Ingredient(
            id = "en:sugar",
            name = "Sugar",
            isVegan = "yes",
            isVegetarian = "yes",
            percentEstimate = 50.0
        ),
        Ingredient(
            id = "en:palm-oil",
            name = "Palm Oil",
            isVegan = "yes",
            isVegetarian = "yes",
            percentEstimate = 20.0
        )
    ),
    nutriments = Nutriments(
        energyKcal100g = 539.0,
        fat100g = 31.0,
        saturatedFat100g = 11.0,
        carbohydrates100g = 57.0,
        sugars100g = 56.0,
        proteins100g = 6.0,
        salt100g = 0.11,
        energyKcalServing = 81.0,
        fatServing = 4.7,
        saturatedFatServing = 1.7,
        carbohydratesServing = 8.6,
        sugarsServing = 8.4,
        proteinsServing = 0.9,
        saltServing = 0.016
    ),
    nutritionInfo = NutritionInfo(
        grade = "E",
        score = 26,
        nutrientLevels = mapOf("fat" to "high", "sugars" to "high"),
        novaGroup = 4
    ),
    additivesInfo = AdditivesInfo(count = 2, tags = listOf("en:e322", "en:e471")),
    allergensInfo = AllergensInfo(tags = listOf("en:milk", "en:nuts")),
    categoriesInfo = CategoriesInfo(tags = listOf("en:spreads", "en:hazelnut-spreads")),
    packaging = listOf(
        Packaging(
            material = "en:plastic",
            shape = "en:jar",
            recycling = "en:recycle",
            numberOfUnits = "1"
        )
    ),
    labelsInfo = LabelsInfo(tags = listOf("en:gluten-free")),
    ecoScoreInfo = EcoScoreInfo(grade = "D", score = 45),
    imageInfo = ImageInfo(
        mainImageUrl = "https://example.com/nutella.jpg",
        frontImageUrl = "https://example.com/nutella_front.jpg",
        frontThumbUrl = "https://example.com/nutella_thumb.jpg"
    )
)