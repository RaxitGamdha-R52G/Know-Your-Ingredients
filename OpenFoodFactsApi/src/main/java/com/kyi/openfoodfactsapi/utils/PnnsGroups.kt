package com.kyi.openfoodfactsapi.utils

import com.kyi.openfoodfactsapi.sources.OffTagged

sealed class PnnsGroup1(val name: String, val subGroups: List<PnnsGroup2>) {
    data object BEVERAGES : PnnsGroup1(
        "Beverages",
        listOf(
            PnnsGroup2.FRUIT_JUICES,
            PnnsGroup2.SWEETENED_BEVERAGES,
            PnnsGroup2.ALCOHOLIC_BEVERAGES,
            PnnsGroup2.UNSWEETENED_BEVERAGES,
            PnnsGroup2.ARTIFICIALLY_SWEETENED_BEVERAGES,
            PnnsGroup2.WATERS_AND_FLAVORED_WATERS,
            PnnsGroup2.FRUIT_NECTARS,
            PnnsGroup2.TEAS_AND_HERBAL_TEAS_AND_COFFEES
        )
    )

    data object CEREALS_AND_POTATOES : PnnsGroup1(
        "Cereals and potatoes",
        listOf(
            PnnsGroup2.BREAKFAST_CEREALS,
            PnnsGroup2.PASTRIES,
            PnnsGroup2.POTATOES,
            PnnsGroup2.CEREALS,
            PnnsGroup2.BREAD
        )
    )

    data object COMPOSITE_FOODS : PnnsGroup1(
        "Composite foods",
        listOf(
            PnnsGroup2.PIZZA_PIES_AND_QUICHE,
            PnnsGroup2.SANDWICHES,
            PnnsGroup2.SOUPS,
            PnnsGroup2.ONE_DISH_MEALS
        )
    )

    data object FAT_AND_SAUCES :
        PnnsGroup1("Fat and sauces", listOf(PnnsGroup2.DRESSINGS_AND_SAUCES, PnnsGroup2.FATS))

    data object FISH_MEAT_AND_EGGS : PnnsGroup1(
        "Fish, meat and eggs",
        listOf(
            PnnsGroup2.PROCESSED_MEAT,
            PnnsGroup2.MEAT,
            PnnsGroup2.FISH_AND_SEAFOOD,
            PnnsGroup2.OFFALS
        )
    )

    data object FRUITS_AND_VEGETABLES : PnnsGroup1(
        "Fruits and vegetables",
        listOf(
            PnnsGroup2.FRUITS,
            PnnsGroup2.VEGETABLES,
            PnnsGroup2.DRIED_FRUITS,
            PnnsGroup2.NUTS,
            PnnsGroup2.LEGUMES
        )
    )

    data object MILK_AND_DAIRIES : PnnsGroup1(
        "Milk and dairies",
        listOf(
            PnnsGroup2.MILK_AND_YOGURT,
            PnnsGroup2.DAIRY_DESSERTS,
            PnnsGroup2.CHEESE,
            PnnsGroup2.PLANT_BASED_MILK_SUBSTITUTES
        )
    )

    data object SALTY_SNACKS : PnnsGroup1(
        "Salty snacks",
        listOf(PnnsGroup2.APPETIZERS, PnnsGroup2.SALTY_AND_FATTY_PRODUCTS)
    )

    data object SUGARY_SNACKS : PnnsGroup1(
        "Sugary snacks",
        listOf(PnnsGroup2.SWEETS, PnnsGroup2.CHOCOLATE_PRODUCTS, PnnsGroup2.ICE_CREAM)
    )

    companion object {
        fun values(): Array<PnnsGroup1> {
            return arrayOf(
                BEVERAGES,
                CEREALS_AND_POTATOES,
                COMPOSITE_FOODS,
                FAT_AND_SAUCES,
                FISH_MEAT_AND_EGGS,
                FRUITS_AND_VEGETABLES,
                MILK_AND_DAIRIES,
                SALTY_SNACKS,
                SUGARY_SNACKS
            )
        }

        fun valueOf(value: String): PnnsGroup1 {
            return when (value) {
                "BEVERAGES" -> BEVERAGES
                "CEREALS_AND_POTATOES" -> CEREALS_AND_POTATOES
                "COMPOSITE_FOODS" -> COMPOSITE_FOODS
                "FAT_AND_SAUCES" -> FAT_AND_SAUCES
                "FISH_MEAT_AND_EGGS" -> FISH_MEAT_AND_EGGS
                "FRUITS_AND_VEGETABLES" -> FRUITS_AND_VEGETABLES
                "MILK_AND_DAIRIES" -> MILK_AND_DAIRIES
                "SALTY_SNACKS" -> SALTY_SNACKS
                "SUGARY_SNACKS" -> SUGARY_SNACKS
                else -> throw IllegalArgumentException("No object com.kyi.openfoodfactsapi.utils.PnnsGroup1.$value")
            }
        }
    }
}

sealed class PnnsGroup2(override val offTag: String, val name: String) : OffTagged {
    data object FRUIT_JUICES : PnnsGroup2("fruit-juices", "Fruit juices")
    data object SWEETENED_BEVERAGES : PnnsGroup2("sweetened-beverages", "Sweetened beverages")
    data object ALCOHOLIC_BEVERAGES : PnnsGroup2("alcoholic-beverages", "Alcoholic beverages")
    data object UNSWEETENED_BEVERAGES : PnnsGroup2("unsweetened-beverages", "Unsweetened beverages")
    data object ARTIFICIALLY_SWEETENED_BEVERAGES :
        PnnsGroup2("artificially-sweetened-beverages", "Artificially sweetened beverages")

    data object WATERS_AND_FLAVORED_WATERS :
        PnnsGroup2("waters-and-flavored-waters", "Waters and flavored waters")

    data object FRUIT_NECTARS : PnnsGroup2("fruit-nectars", "Fruits nectars")
    data object TEAS_AND_HERBAL_TEAS_AND_COFFEES :
        PnnsGroup2("teas-and-herbal-teas-and-coffees", "Teas and herbal teas and coffees")

    data object BREAKFAST_CEREALS : PnnsGroup2("breakfast-cereals", "Breakfast cereals")
    data object PASTRIES : PnnsGroup2("pastries", "Pastries")
    data object POTATOES : PnnsGroup2("potatoes", "Potatoes")
    data object CEREALS : PnnsGroup2("cereals", "Cereals")
    data object BREAD : PnnsGroup2("bread", "Bread")
    data object PIZZA_PIES_AND_QUICHE :
        PnnsGroup2("pizza-pies-and-quiche", "Pizza, pies and quiche")

    data object SANDWICHES : PnnsGroup2("sandwiches", "Sandwiches")
    data object SOUPS : PnnsGroup2("soups", "Soups")
    data object ONE_DISH_MEALS : PnnsGroup2("one-dish-meals", "One dish meals")
    data object DRESSINGS_AND_SAUCES : PnnsGroup2("dressings-and-sauces", "Dressings and sauces")
    data object FATS : PnnsGroup2("fats", "Fats")
    data object PROCESSED_MEAT : PnnsGroup2("processed-meat", "Processed meat")
    data object MEAT : PnnsGroup2("meat", "Meat")
    data object FISH_AND_SEAFOOD : PnnsGroup2("fish-and-seafood", "Fish and seafood")
    data object OFFALS : PnnsGroup2("offals", "Offals")
    data object FRUITS : PnnsGroup2("fruits", "Fruits")
    data object VEGETABLES : PnnsGroup2("vegetables", "Vegetables")
    data object DRIED_FRUITS : PnnsGroup2("dried-fruits", "Dried fruits")
    data object NUTS : PnnsGroup2("nuts", "Nuts")
    data object LEGUMES : PnnsGroup2("legumes", "Legumes")
    data object MILK_AND_YOGURT : PnnsGroup2("milk-and-yogurt", "Milk and yogurt")
    data object DAIRY_DESSERTS : PnnsGroup2("dairy-desserts", "Dairy desserts")
    data object CHEESE : PnnsGroup2("cheese", "Cheese")
    data object PLANT_BASED_MILK_SUBSTITUTES :
        PnnsGroup2("plant-based-milk-substitutes", "Plant based milk substitutes")

    data object APPETIZERS : PnnsGroup2("appetizers", "Appetizers")
    data object SALTY_AND_FATTY_PRODUCTS :
        PnnsGroup2("salty-and-fatty-products", "Salty and fatty products")

    data object SWEETS : PnnsGroup2("sweets", "Sweets")
    data object CHOCOLATE_PRODUCTS : PnnsGroup2("chocolate-products", "Chocolate products")
    data object ICE_CREAM : PnnsGroup2("ice-cream", "Ice cream")
    companion object {
        fun values(): Array<PnnsGroup2> {
            return arrayOf(
                FRUIT_JUICES,
                SWEETENED_BEVERAGES,
                ALCOHOLIC_BEVERAGES,
                UNSWEETENED_BEVERAGES,
                ARTIFICIALLY_SWEETENED_BEVERAGES,
                WATERS_AND_FLAVORED_WATERS,
                FRUIT_NECTARS,
                TEAS_AND_HERBAL_TEAS_AND_COFFEES,
                BREAKFAST_CEREALS,
                PASTRIES,
                POTATOES,
                CEREALS,
                BREAD,
                PIZZA_PIES_AND_QUICHE,
                SANDWICHES,
                SOUPS,
                ONE_DISH_MEALS,
                DRESSINGS_AND_SAUCES,
                FATS,
                PROCESSED_MEAT,
                MEAT,
                FISH_AND_SEAFOOD,
                OFFALS,
                FRUITS,
                VEGETABLES,
                DRIED_FRUITS,
                NUTS,
                LEGUMES,
                MILK_AND_YOGURT,
                DAIRY_DESSERTS,
                CHEESE,
                PLANT_BASED_MILK_SUBSTITUTES,
                APPETIZERS,
                SALTY_AND_FATTY_PRODUCTS,
                SWEETS,
                CHOCOLATE_PRODUCTS,
                ICE_CREAM
            )
        }

        fun valueOf(value: String): PnnsGroup2 {
            return when (value) {
                "FRUIT_JUICES" -> FRUIT_JUICES
                "SWEETENED_BEVERAGES" -> SWEETENED_BEVERAGES
                "ALCOHOLIC_BEVERAGES" -> ALCOHOLIC_BEVERAGES
                "UNSWEETENED_BEVERAGES" -> UNSWEETENED_BEVERAGES
                "ARTIFICIALLY_SWEETENED_BEVERAGES" -> ARTIFICIALLY_SWEETENED_BEVERAGES
                "WATERS_AND_FLAVORED_WATERS" -> WATERS_AND_FLAVORED_WATERS
                "FRUIT_NECTARS" -> FRUIT_NECTARS
                "TEAS_AND_HERBAL_TEAS_AND_COFFEES" -> TEAS_AND_HERBAL_TEAS_AND_COFFEES
                "BREAKFAST_CEREALS" -> BREAKFAST_CEREALS
                "PASTRIES" -> PASTRIES
                "POTATOES" -> POTATOES
                "CEREALS" -> CEREALS
                "BREAD" -> BREAD
                "PIZZA_PIES_AND_QUICHE" -> PIZZA_PIES_AND_QUICHE
                "SANDWICHES" -> SANDWICHES
                "SOUPS" -> SOUPS
                "ONE_DISH_MEALS" -> ONE_DISH_MEALS
                "DRESSINGS_AND_SAUCES" -> DRESSINGS_AND_SAUCES
                "FATS" -> FATS
                "PROCESSED_MEAT" -> PROCESSED_MEAT
                "MEAT" -> MEAT
                "FISH_AND_SEAFOOD" -> FISH_AND_SEAFOOD
                "OFFALS" -> OFFALS
                "FRUITS" -> FRUITS
                "VEGETABLES" -> VEGETABLES
                "DRIED_FRUITS" -> DRIED_FRUITS
                "NUTS" -> NUTS
                "LEGUMES" -> LEGUMES
                "MILK_AND_YOGURT" -> MILK_AND_YOGURT
                "DAIRY_DESSERTS" -> DAIRY_DESSERTS
                "CHEESE" -> CHEESE
                "PLANT_BASED_MILK_SUBSTITUTES" -> PLANT_BASED_MILK_SUBSTITUTES
                "APPETIZERS" -> APPETIZERS
                "SALTY_AND_FATTY_PRODUCTS" -> SALTY_AND_FATTY_PRODUCTS
                "SWEETS" -> SWEETS
                "CHOCOLATE_PRODUCTS" -> CHOCOLATE_PRODUCTS
                "ICE_CREAM" -> ICE_CREAM
                else -> throw IllegalArgumentException("No object com.kyi.openfoodfactsapi.utils.PnnsGroup2.$value")
            }
        }
    }
}