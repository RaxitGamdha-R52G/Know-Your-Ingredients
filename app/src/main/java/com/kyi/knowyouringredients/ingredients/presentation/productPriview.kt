package com.kyi.knowyouringredients.ingredients.presentation

import com.kyi.knowyouringredients.ingredients.domain.Ingredient
import com.kyi.knowyouringredients.ingredients.domain.Nutriments
import com.kyi.knowyouringredients.ingredients.domain.Packaging
import com.kyi.knowyouringredients.ingredients.domain.Product

// Internal preview data in domain model form
internal val productPreview = Product(
    code = "3017620422003",
    productName = "Nutella",
    brands = "Nutella, Ferrero",
    packaging = listOf(
        Packaging(
            material = "en:clear-glass",
            numberOfUnits = "1",
            quantityValue = "400",
            quantityUnit = "g",
            shape = "en:pot",
            recycling = "en:recycle-in-glass-bin",
            weightMeasured = 199.94
        ),
        Packaging(
            material = "en:pp-5-polypropylene",
            numberOfUnits = "1",
            quantityValue = "400",
            quantityUnit = "g",
            shape = "en:lid",
            recycling = "en:recycle-with-plastics-metal-and-bricks",
            weightMeasured = 6.8
        ),
        Packaging(
            material = "en:non-corrugated-cardboard",
            numberOfUnits = "1",
            quantityValue = "400",
            quantityUnit = "g",
            shape = "en:backing",
            recycling = "en:recycle-in-paper-bin",
            weightMeasured = 1.8
        )
    ),
    ingredients = listOf(
        Ingredient(
            id = "en:sugar",
            name = "Sucre",
            isVegan = "yes",
            isVegetarian = "yes",
            percentEstimate = 38.35,
            percentMin = 16.7,
            percentMax = 60.0,
            ciqualFoodCode = "31016",
            ecobalyseCode = "sugar",
            isInTaxonomy = 1
        ),
        Ingredient(
            id = "en:palm-oil",
            name = "huile de palme",
            isVegan = "yes",
            isVegetarian = "yes",
            percentEstimate = 24.75,
            percentMin = 13.0,
            percentMax = 36.5,
            ciqualFoodCode = "16129",
            ecobalyseCode = "refined-palm-oil",
            fromPalmOil = "yes",
            isInTaxonomy = 1
        ),
        Ingredient(
            id = "en:hazelnut",
            name = "NOISETTES",
            isVegan = "yes",
            isVegetarian = "yes",
            percent = 13.0,
            percentEstimate = 13.0,
            percentMin = 13.0,
            percentMax = 13.0,
            ciqualFoodCode = "15004",
            ecobalyseCode = "hazelnut-unshelled-non-eu",
            isInTaxonomy = 1
        ),
        Ingredient(
            id = "en:emulsifier",
            name = "émulsifiants",
            isVegan = "maybe",
            isVegetarian = "maybe",
            percentEstimate = 3.3,
            percentMin = 0.0,
            percentMax = 6.6,
            isInTaxonomy = 1,
            subIngredients = listOf(
                Ingredient(
                    id = "en:soya-lecithin",
                    name = "lécithines de SOJA",
                    isVegan = "yes",
                    isVegetarian = "yes",
                    percentEstimate = 3.3,
                    percentMin = 0.0,
                    percentMax = 6.6,
                    ciqualFoodCode = "42200",
                    isInTaxonomy = 1
                )
            )
        )
    ),
    nutriments = Nutriments(
        energyKcal100g = 539.0,
        fat100g = 30.9,
        saturatedFat100g = 10.6,
        carbohydrates100g = 57.5,
        sugars100g = 56.3,
        proteins100g = 6.3,
        salt100g = 0.107,
        energyKcalServing = 80.8,
        fatServing = 4.63,
        saturatedFatServing = 1.59,
        carbohydratesServing = 8.62,
        sugarsServing = 8.44,
        proteinsServing = 0.945,
        saltServing = 0.016,
        energyKcalUnit = "kcal",
        fatUnit = "g",
        carbohydratesUnit = "g",
        sugarsUnit = "g",
        proteinsUnit = "g",
        saltUnit = "g",
        novaGroup100g = 4,
        nutritionScoreFr100g = 26, // From API's ecoscore_data
        carbonFootprint100g = 135.0, // Estimated from previous data
        fruitsVeggiesNuts100g = 13.0
    ),
    nutritionGrade = "e", // From API's nutrition_grade_fr (assumed from score)
    nutritionScore = 26, // From API's nutrition_score_fr_100g (assumed)
    additivesN = 2,
    additivesTags = listOf("en:e322", "en:e322i"),
    allergensTags = listOf("en:milk", "en:nuts", "en:soybeans"),
    categoriesTags = listOf(
        "en:breakfasts",
        "en:spreads",
        "en:sweet-spreads",
        "fr:pates-a-tartiner",
        "en:hazelnut-spreads",
        "en:chocolate-spreads",
        "en:cocoa-and-hazelnuts-spreads"
    ),
    quantity = "400 g",
    servingSize = "15 g"
)