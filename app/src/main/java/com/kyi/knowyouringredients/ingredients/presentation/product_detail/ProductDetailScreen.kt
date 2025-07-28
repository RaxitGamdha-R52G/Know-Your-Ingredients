package com.kyi.knowyouringredients.ingredients.presentation.product_detail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kyi.knowyouringredients.R
import com.kyi.knowyouringredients.ingredients.presentation.components.NutritionGrade
import com.kyi.knowyouringredients.ingredients.presentation.components.ProductImage
import com.kyi.knowyouringredients.ingredients.presentation.models.ProductUI
import com.kyi.knowyouringredients.ingredients.presentation.productPreview
import com.kyi.knowyouringredients.ingredients.presentation.product_detail.components.DetailCard
import com.kyi.knowyouringredients.ingredients.presentation.product_detail.components.InfoText
import com.kyi.knowyouringredients.ingredients.presentation.product_list.ProductListState
import com.kyi.knowyouringredients.ingredients.presentation.search.SearchScreenState
import com.kyi.knowyouringredients.ui.theme.KnowYourIngredientsTheme

//@Composable
//fun ProductDetailScreen(
//    state: ProductListState,
//    modifier: Modifier = Modifier,
//    onBack: () -> Unit = {},
//    innerPadding: PaddingValues = PaddingValues(0.dp)
//) {
//    var isNavigatingBack by remember { mutableStateOf(false) }
//    val selectedProduct = when (state) {
//        is ProductListState.Search -> state.state.selectedProduct
//        is ProductListState.Scan -> state.state.selectedProduct
//    }
//
//    // Handle system back button
//    BackHandler(enabled = !isNavigatingBack, onBack = {
//        Log.d("ProductDetailScreen", "System back button pressed")
//        isNavigatingBack = true
//        onBack()
//    })
//
//    Log.d("ProductDetailScreen", "Product: ${selectedProduct?.productName ?: "null"}, isNavigatingBack: $isNavigatingBack")
//
//    if (state.isLoading) {
//        Box(
//            modifier = modifier
//                .fillMaxSize()
//                .padding(innerPadding),
//            contentAlignment = Alignment.Center
//        ) {
//            CircularProgressIndicator()
//        }
//    } else if (selectedProduct != null && !isNavigatingBack) {
//        val productUI = selectedProduct
//        LazyColumn(
//            modifier = modifier
//                .fillMaxSize()
//                .padding(16.dp),
//            verticalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//            item {
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    ProductImage(
//                        imageUrl = productUI.imageUrl,
//                        size = 120.dp,
//                        contentDescription = stringResource(R.string.product_image, productUI.productName)
//                    )
//                    Spacer(modifier = Modifier.width(16.dp))
//                    Column {
//                        Text(
//                            text = productUI.productName,
//                            style = MaterialTheme.typography.headlineMedium,
//                            fontWeight = FontWeight.Bold,
//                            color = MaterialTheme.colorScheme.primary
//                        )
//                        Spacer(modifier = Modifier.height(4.dp))
//                        Text(
//                            text = productUI.brands.joinToString(", "),
//                            style = MaterialTheme.typography.bodyLarge,
//                            color = MaterialTheme.colorScheme.onSurfaceVariant
//                        )
//                    }
//                }
//            }
//
//            item {
//                Card(
//                    shape = RoundedCornerShape(12.dp),
//                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
//                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
//                ) {
//                    Column(modifier = Modifier.padding(16.dp)) {
//                        Text(
//                            text = stringResource(R.string.product_info),
//                            style = MaterialTheme.typography.titleLarge,
//                            fontWeight = FontWeight.SemiBold
//                        )
//                        Spacer(modifier = Modifier.height(8.dp))
//                        Text(
//                            text = stringResource(R.string.barcode, productUI.code),
//                            style = MaterialTheme.typography.bodyMedium
//                        )
//                        productUI.quantity?.let {
//                            Text(
//                                text = stringResource(R.string.quantity, it),
//                                style = MaterialTheme.typography.bodyMedium
//                            )
//                        }
//                        productUI.servingSize?.let {
//                            Text(
//                                text = stringResource(R.string.serving_size, it),
//                                style = MaterialTheme.typography.bodyMedium
//                            )
//                        }
//                    }
//                }
//            }
//
//            item {
//                Card(
//                    shape = RoundedCornerShape(12.dp),
//                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
//                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
//                ) {
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(16.dp),
//                        horizontalArrangement = Arrangement.SpaceEvenly
//                    ) {
//                        if (productUI.nutritionGrade != "N/A") {
//                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
////                                Box(
////                                    modifier = Modifier
////                                        .size(60.dp)
////                                        .clip(CircleShape)
////                                        .background(
////                                            color = when (productUI.nutritionGrade.lowercase()) {
////                                                "a" -> Color(0xFF00C853)
////                                                "b" -> Color(0xFF76FF03)
////                                                "c" -> Color(0xFFFFC107)
////                                                "d" -> Color(0xFFFF5722)
////                                                "e" -> Color(0xFFFF0000)
////                                                "u" -> Color(0xD2696666)
////                                                else -> MaterialTheme.colorScheme.onSurfaceVariant
////                                            }
////                                        )
////                                        .border(
////                                            width = 2.dp,
////                                            color = MaterialTheme.colorScheme.outline,
////                                            shape = CircleShape
////                                        )
////                                        .padding(8.dp),
////                                    contentAlignment = Alignment.Center
////                                ) {
////                                    Text(
////                                        text = productUI.nutritionGrade,
////                                        style = MaterialTheme.typography.titleLarge,
////                                        fontWeight = FontWeight.Bold,
////                                        color = MaterialTheme.colorScheme.onPrimary
////                                    )
////                                }
//                                NutritionGrade(
//                                    productUI = productUI,
//                                    size = 60.dp,
//                                    style = MaterialTheme.typography.titleLarge
//                                )
//                                Spacer(modifier = Modifier.height(4.dp))
//                                Text(
//                                    text = stringResource(R.string.nutrition_grade),
//                                    style = MaterialTheme.typography.labelMedium
//                                )
//                            }
//                        }
//
//                        if (productUI.ecoScoreGrade != "N/A") {
//                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                                Box(
//                                    modifier = Modifier
//                                        .size(60.dp)
//                                        .clip(CircleShape)
//                                        .background(
//                                            color = when (productUI.ecoScoreGrade.lowercase()) {
//                                                "a" -> Color(0xFF00C853)
//                                                "b" -> Color(0xFF76FF03)
//                                                "c" -> Color(0xFFFFC107)
//                                                "d" -> Color(0xFFFF5722)
//                                                "e" -> Color(0xFFFF0000)
//                                                "u" -> Color(0xD2696666)
//                                                else -> MaterialTheme.colorScheme.onSurfaceVariant
//                                            }
//                                        )
//                                        .border(
//                                            width = 2.dp,
//                                            color = MaterialTheme.colorScheme.outline,
//                                            shape = CircleShape
//                                        )
//                                        .padding(8.dp),
//                                    contentAlignment = Alignment.Center
//                                ) {
//                                    Text(
//                                        text = productUI.ecoScoreGrade,
//                                        style = MaterialTheme.typography.titleLarge,
//                                        fontWeight = FontWeight.Bold,
//                                        color = MaterialTheme.colorScheme.onPrimary
//                                    )
//                                }
//                                Spacer(modifier = Modifier.height(4.dp))
//                                Text(
//                                    text = stringResource(R.string.eco_score),
//                                    style = MaterialTheme.typography.labelMedium
//                                )
//                            }
//                        }
//                    }
//                }
//            }
//
//            item {
//                Card(
//                    shape = RoundedCornerShape(12.dp),
//                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
//                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
//                ) {
//                    Column(modifier = Modifier.padding(16.dp)) {
//                        Text(
//                            text = stringResource(R.string.ingredients),
//                            style = MaterialTheme.typography.titleLarge,
//                            fontWeight = FontWeight.SemiBold
//                        )
//                        Spacer(modifier = Modifier.height(8.dp))
//                        if (productUI.ingredients.isEmpty()) {
//                            Text(
//                                text = stringResource(R.string.no_ingredients_listed),
//                                style = MaterialTheme.typography.bodyMedium
//                            )
//                        } else {
//                            productUI.ingredients.forEach { ingredient ->
//                                Text(
//                                    text = stringResource(R.string.ingredient_item, ingredient.name, ingredient.percent),
//                                    style = MaterialTheme.typography.bodyMedium
//                                )
//                                if (ingredient.subIngredients.isNotEmpty()) {
//                                    ingredient.subIngredients.forEach { subIngredient ->
//                                        Text(
//                                            text = stringResource(R.string.sub_ingredient_item, subIngredient.name),
//                                            style = MaterialTheme.typography.bodySmall
//                                        )
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//
//            item {
//                Card(
//                    shape = RoundedCornerShape(12.dp),
//                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
//                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
//                ) {
//                    Column(modifier = Modifier.padding(16.dp)) {
//                        Text(
//                            text = stringResource(R.string.nutrition_per_100g_serving),
//                            style = MaterialTheme.typography.titleLarge,
//                            fontWeight = FontWeight.SemiBold
//                        )
//                        Spacer(modifier = Modifier.height(8.dp))
//                        Text(
//                            text = stringResource(R.string.energy, productUI.energyKcal100g, productUI.energyKcalServing),
//                            style = MaterialTheme.typography.bodyMedium
//                        )
//                        Text(
//                            text = stringResource(R.string.fat, productUI.fat100g, productUI.fatServing),
//                            style = MaterialTheme.typography.bodyMedium
//                        )
//                        Text(
//                            text = stringResource(R.string.saturated_fat, productUI.saturatedFat100g, productUI.saturatedFatServing),
//                            style = MaterialTheme.typography.bodyMedium
//                        )
//                        Text(
//                            text = stringResource(R.string.carbohydrates, productUI.carbohydrates100g, productUI.carbohydratesServing),
//                            style = MaterialTheme.typography.bodyMedium
//                        )
//                        Text(
//                            text = stringResource(R.string.sugars, productUI.sugars100g, productUI.sugarsServing),
//                            style = MaterialTheme.typography.bodyMedium
//                        )
//                        Text(
//                            text = stringResource(R.string.proteins, productUI.proteins100g, productUI.proteinsServing),
//                            style = MaterialTheme.typography.bodyMedium
//                        )
//                        Text(
//                            text = stringResource(R.string.salt, productUI.salt100g, productUI.saltServing),
//                            style = MaterialTheme.typography.bodyMedium
//                        )
//                    }
//                }
//            }
//
//            item {
//                Card(
//                    shape = RoundedCornerShape(12.dp),
//                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
//                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
//                ) {
//                    Column(modifier = Modifier.padding(16.dp)) {
//                        Text(
//                            text = stringResource(R.string.additional_info),
//                            style = MaterialTheme.typography.titleLarge,
//                            fontWeight = FontWeight.SemiBold
//                        )
//                        Spacer(modifier = Modifier.height(8.dp))
//                        if (productUI.additivesTags.isNotEmpty()) {
//                            Text(
//                                text = stringResource(R.string.additives, productUI.additivesTags.joinToString(", "), productUI.additivesCount),
//                                style = MaterialTheme.typography.bodyMedium
//                            )
//                        }
//                        if (productUI.allergensTags.isNotEmpty()) {
//                            Text(
//                                text = stringResource(R.string.allergens, productUI.allergensTags.joinToString(", ")),
//                                style = MaterialTheme.typography.bodyMedium
//                            )
//                        }
//                        if (productUI.categoriesTags.isNotEmpty()) {
//                            Text(
//                                text = stringResource(R.string.categories, productUI.categoriesTags.joinToString(", ")),
//                                style = MaterialTheme.typography.bodyMedium
//                            )
//                        }
//                        if (productUI.labelsTags.isNotEmpty()) {
//                            Text(
//                                text = stringResource(R.string.labels, productUI.labelsTags.joinToString(", ")),
//                                style = MaterialTheme.typography.bodyMedium
//                            )
//                        }
//                        if (productUI.packaging.isNotEmpty()) {
//                            Text(
//                                text = stringResource(R.string.packaging),
//                                style = MaterialTheme.typography.bodyMedium
//                            )
//                            productUI.packaging.forEach { pkg ->
//                                Text(
//                                    text = stringResource(R.string.packaging_item, pkg.material, pkg.numberOfUnits, pkg.recycling),
//                                    style = MaterialTheme.typography.bodySmall
//                                )
//                            }
//                        }
//                    }
//                }
//            }
//
//            item {
//                Button(
//                    onClick = {
//                        Log.d("ProductDetailScreen", "UI back button pressed")
//                        isNavigatingBack = true
//                        onBack()
//                    },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 16.dp)
//                ) {
//                    Text(stringResource(R.string.back))
//                }
//            }
//        }
//    } else if (!isNavigatingBack) {
//        Box(
//            modifier = modifier
//                .fillMaxSize()
//                .padding(innerPadding),
//            contentAlignment = Alignment.Center
//        ) {
//            Text(
//                text = stringResource(R.string.product_not_found),
//                style = MaterialTheme.typography.bodyLarge,
//                textAlign = TextAlign.Center
//            )
//        }
//    }
//}


@Composable
fun ProductDetailScreen(
    state: ProductListState,
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    innerPadding: PaddingValues = PaddingValues(0.dp)
) {
    var isNavigatingBack by remember { mutableStateOf(false) }
    val selectedProduct = when (state) {
        is ProductListState.Search -> state.state.selectedProduct
        is ProductListState.Scan -> state.state.selectedProduct
    }

    BackHandler(enabled = !isNavigatingBack, onBack = {
        isNavigatingBack = true
        onBack()
    })

    if (state.isLoading) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (selectedProduct != null && !isNavigatingBack) {
        val productUI = selectedProduct
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ProductImage(
                        imageUrl = productUI.imageUrl,
                        size = 120.dp,
                        contentDescription = stringResource(
                            R.string.product_image,
                            productUI.productName
                        )
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = productUI.productName,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = productUI.brands.joinToString(),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = stringResource(R.string.barcode, productUI.code),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    if (productUI.nutritionGrade != "N/A") {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            NutritionGrade(
                                productUI = productUI,
                                size = 60.dp,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = stringResource(R.string.nutrition_grade),
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                    if (productUI.ecoScoreGrade != "N/A") {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Box(
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.secondary)
                                    .padding(8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = productUI.ecoScoreGrade,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                            Text(
                                text = stringResource(R.string.eco_score),
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                }
            }

            item {
                DetailCard(title = stringResource(R.string.ingredients)) {
                    if (productUI.ingredients.isEmpty()) {
                        Text(text = stringResource(R.string.no_ingredients_listed))
                    } else {
                        productUI.ingredients.forEach { ingredient ->
                            Text(text = "- ${ingredient.name} (${ingredient.percent}%)")
                            ingredient.subIngredients.forEach { sub ->
                                Text(
                                    text = "   • ${sub.name}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            }

            item {
                DetailCard(title = stringResource(R.string.nutrition_per_100g_serving)) {
                    InfoText(R.string.energy, productUI.energyKcal100g, productUI.energyKcalServing)
                    InfoText(R.string.fat, productUI.fat100g, productUI.fatServing)
                    InfoText(
                        R.string.saturated_fat,
                        productUI.saturatedFat100g,
                        productUI.saturatedFatServing
                    )
                    InfoText(
                        R.string.carbohydrates,
                        productUI.carbohydrates100g,
                        productUI.carbohydratesServing
                    )
                    InfoText(R.string.sugars, productUI.sugars100g, productUI.sugarsServing)
                    InfoText(R.string.proteins, productUI.proteins100g, productUI.proteinsServing)
                    InfoText(R.string.salt, productUI.salt100g, productUI.saltServing)
                }
            }

            item {
                DetailCard(title = stringResource(R.string.additional_info)) {
                    if (productUI.additivesTags.isNotEmpty())
                        Text(
                            text = stringResource(
                                R.string.additives,
                                productUI.additivesTags.joinToString(),
                                productUI.additivesCount
                            )
                        )
                    if (productUI.allergensTags.isNotEmpty())
                        Text(
                            text = stringResource(
                                R.string.allergens,
                                productUI.allergensTags.joinToString()
                            )
                        )
                    if (productUI.categoriesTags.isNotEmpty())
                        Text(
                            text = stringResource(
                                R.string.categories,
                                productUI.categoriesTags.joinToString()
                            )
                        )
                    if (productUI.labelsTags.isNotEmpty())
                        Text(
                            text = stringResource(
                                R.string.labels,
                                productUI.labelsTags.joinToString()
                            )
                        )
                }
            }

            item {
                Button(
                    onClick = {
                        isNavigatingBack = true
                        onBack()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.back))
                }
            }
        }
    } else if (!isNavigatingBack) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.product_not_found),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun ProductDetailScreenPreview() {
    KnowYourIngredientsTheme {
        ProductDetailScreen(
            state = ProductListState.Search(
                state = SearchScreenState(
                    selectedProduct = ProductUI.fromDomain(productPreview)
                )
            ),
            innerPadding = PaddingValues(0.dp)
        )
    }
}