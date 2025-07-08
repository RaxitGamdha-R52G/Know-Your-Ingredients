package com.kyi.knowyouringredients.ingredients.presentation.product_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kyi.knowyouringredients.ingredients.presentation.components.ProductImage
import com.kyi.knowyouringredients.ingredients.presentation.models.ProductUI
import com.kyi.knowyouringredients.ingredients.presentation.productPreview
import com.kyi.knowyouringredients.ingredients.presentation.product_list.ProductListState
import com.kyi.knowyouringredients.ui.theme.KnowYourIngredientsTheme

@Composable
fun ProductDetailScreen(
    state: ProductListState,
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    innerPadding: PaddingValues = PaddingValues(0.dp)
) {
    if (state.isLoading) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (state.selectedProduct != null) {
        val productUI = state.selectedProduct
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
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
                        contentDescription = "Product image for ${productUI.productName}"
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = productUI.productName,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = productUI.brands.joinToString(", "),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Product Info",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Barcode: ${productUI.code}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        productUI.quantity?.let {
                            Text(
                                text = "Quantity: $it",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        productUI.servingSize?.let {
                            Text(
                                text = "Serving Size: $it",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }

            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        if (productUI.nutritionGrade != "N/A") {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Box(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .clip(CircleShape)
                                        .background(
                                            color = when (productUI.nutritionGrade.lowercase()) {
                                                "a" -> Color(0xFF00C853)
                                                "b" -> Color(0xFF76FF03)
                                                "c" -> Color(0xFFFFC107)
                                                "d" -> Color(0xFFFF5722)
                                                "e" -> Color(0xFFFF0000)
                                                else -> MaterialTheme.colorScheme.onSurfaceVariant
                                            }
                                        )
                                        .border(
                                            width = 2.dp,
                                            color = MaterialTheme.colorScheme.outline,
                                            shape = CircleShape
                                        )
                                        .padding(8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = productUI.nutritionGrade,
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Nutrition Grade",
                                    style = MaterialTheme.typography.labelMedium
                                )
                            }
                        }

                        if (productUI.ecoScoreGrade != "N/A") {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Box(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .clip(CircleShape)
                                        .background(
                                            color = when (productUI.ecoScoreGrade.lowercase()) {
                                                "a" -> Color(0xFF00C853)
                                                "b" -> Color(0xFF76FF03)
                                                "c" -> Color(0xFFFFC107)
                                                "d" -> Color(0xFFFF5722)
                                                "e" -> Color(0xFFFF0000)
                                                else -> MaterialTheme.colorScheme.onSurfaceVariant
                                            }
                                        )
                                        .border(
                                            width = 2.dp,
                                            color = MaterialTheme.colorScheme.outline,
                                            shape = CircleShape
                                        )
                                        .padding(8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = productUI.ecoScoreGrade,
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "EcoScore",
                                    style = MaterialTheme.typography.labelMedium
                                )
                            }
                        }
                    }
                }
            }

            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Ingredients",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        if (productUI.ingredients.isEmpty()) {
                            Text(
                                text = "No ingredients listed",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        } else {
                            productUI.ingredients.forEach { ingredient ->
                                Text(
                                    text = "- ${ingredient.name} (${ingredient.percent})",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                if (ingredient.subIngredients.isNotEmpty()) {
                                    ingredient.subIngredients.forEach { subIngredient ->
                                        Text(
                                            text = "  * ${subIngredient.name}",
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Nutrition (per 100g / per serving)",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Energy: ${productUI.energyKcal100g} / ${productUI.energyKcalServing}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Fat: ${productUI.fat100g} / ${productUI.fatServing}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Saturated Fat: ${productUI.saturatedFat100g} / ${productUI.saturatedFatServing}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Carbohydrates: ${productUI.carbohydrates100g} / ${productUI.carbohydratesServing}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Sugars: ${productUI.sugars100g} / ${productUI.sugarsServing}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Proteins: ${productUI.proteins100g} / ${productUI.proteinsServing}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Salt: ${productUI.salt100g} / ${productUI.saltServing}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Additional Info",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        if (productUI.additivesTags.isNotEmpty()) {
                            Text(
                                text = "Additives: ${productUI.additivesTags.joinToString(", ")} (${productUI.additivesCount})",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        if (productUI.allergensTags.isNotEmpty()) {
                            Text(
                                text = "Allergens: ${productUI.allergensTags.joinToString(", ")}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        if (productUI.categoriesTags.isNotEmpty()) {
                            Text(
                                text = "Categories: ${productUI.categoriesTags.joinToString(", ")}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        if (productUI.labelsTags.isNotEmpty()) {
                            Text(
                                text = "Labels: ${productUI.labelsTags.joinToString(", ")}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        if (productUI.packaging.isNotEmpty()) {
                            Text(text = "Packaging:", style = MaterialTheme.typography.bodyMedium)
                            productUI.packaging.forEach { pkg ->
                                Text(
                                    text = "  - ${pkg.material}, ${pkg.numberOfUnits} units, ${pkg.recycling}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductDetailScreenPreview() {
    KnowYourIngredientsTheme {
        ProductDetailScreen(
            state = ProductListState(
                selectedProduct = ProductUI.fromDomain(productPreview)
            ),
            innerPadding = PaddingValues(0.dp)
        )
    }
}