package com.kyi.knowyouringredients.ingredients.presentation.product_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.kyi.knowyouringredients.ui.theme.KnowYourIngredientsTheme

@Composable
fun ProductDetailScreen(
    productUI: ProductUI,
    modifier: Modifier = Modifier
) {
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
                    imageUrl = productUI.imageInfo?.frontImageUrl
                        ?: productUI.imageInfo?.mainImageUrl,
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
                    Text("Barcode: ${productUI.code}", style = MaterialTheme.typography.bodyMedium)
                    productUI.quantity?.let {
                        Text(
                            "Quantity: $it",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    productUI.servingSize?.let {
                        Text(
                            "Serving Size: $it",
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
                    productUI.nutritionInfo?.grade?.let { grade ->
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Box(
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(CircleShape)
                                    .background(
                                        color = when (grade.lowercase()) {
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
                                    text = grade.uppercase(),
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Nutrition Grade", style = MaterialTheme.typography.labelMedium)
                        }
                    }

                    productUI.ecoScoreInfo?.grade?.let { ecoGrade ->
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Box(
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(CircleShape)
                                    .background(
                                        color = when (ecoGrade.lowercase()) {
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
                                    text = ecoGrade.uppercase(),
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("EcoScore", style = MaterialTheme.typography.labelMedium)
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
                        Text("No ingredients listed", style = MaterialTheme.typography.bodyMedium)
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

        productUI.nutriments?.let { nutriments ->
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
                            "Energy: ${nutriments.energyKcal100g} / ${nutriments.energyKcalServing}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            "Fat: ${nutriments.fat100g} / ${nutriments.fatServing}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            "Saturated Fat: ${nutriments.saturatedFat100g} / ${nutriments.saturatedFatServing}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            "Carbohydrates: ${nutriments.carbohydrates100g} / ${nutriments.carbohydratesServing}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            "Sugars: ${nutriments.sugars100g} / ${nutriments.sugarsServing}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            "Proteins: ${nutriments.proteins100g} / ${nutriments.proteinsServing}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            "Salt: ${nutriments.salt100g} / ${nutriments.saltServing}",
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
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Additional Info",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    productUI.additivesInfo?.let {
                        Text(
                            "Additives: ${it.tags.joinToString(", ")} (${it.count})",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    productUI.allergensInfo?.let {
                        Text(
                            "Allergens: ${it.tags.joinToString(", ")}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    productUI.categoriesInfo?.let {
                        Text(
                            "Categories: ${it.tags.joinToString(", ")}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    productUI.labelsInfo?.let {
                        Text(
                            "Labels: ${it.tags.joinToString(", ")}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    productUI.packaging.takeIf { it.isNotEmpty() }?.let {
                        Text("Packaging:", style = MaterialTheme.typography.bodyMedium)
                        it.forEach { pkg ->
                            Text(
                                "  - ${pkg.material}, ${pkg.numberOfUnits} units, ${pkg.recycling}",
                                style = MaterialTheme.typography.bodySmall
                            )
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
            productUI = ProductUI.fromDomain(productPreview)
        )
    }
}