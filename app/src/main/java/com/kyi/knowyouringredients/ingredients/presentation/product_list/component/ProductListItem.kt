package com.kyi.knowyouringredients.ingredients.presentation.product_list.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kyi.knowyouringredients.ingredients.presentation.components.ProductImage
import com.kyi.knowyouringredients.ingredients.presentation.models.ProductUI
import com.kyi.knowyouringredients.ingredients.presentation.productPreview
import com.kyi.knowyouringredients.ui.theme.KnowYourIngredientsTheme

@Composable
fun ProductListItem(
    productUI: ProductUI,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .semantics { contentDescription = "Product: ${productUI.productName}" },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp,
            focusedElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        val primaryColor = Color.Green
        val secondaryColor = Color.Cyan
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.surface,
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f)
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(1000f, 1000f)
                    )
                )
                .drawBehind {
                    drawCircle(
                        color = primaryColor.copy(alpha = 0.2f),
                        radius = size.width * 0.8f,
                        center = Offset(size.width * 0.9f, size.height * 0.2f)
                    )
                    drawCircle(
                        color = secondaryColor.copy(alpha = 0.2f),
                        radius = size.width * 0.6f,
                        center = Offset(size.width * 0.1f, size.height * 0.8f)
                    )
                }
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                productUI.frontThumbUrl?.let { thumbUrl ->
                    ProductImage(
                        imageUrl = thumbUrl,
                        size = 60.dp,
                        contentDescription = "Product thumbnail for ${productUI.productName}"
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = productUI.productName,
                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 22.sp),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color.Transparent)
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.outline,
                                    shape = RoundedCornerShape(6.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (productUI.brands.size == 1) "Company" else "Companies",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = productUI.brands.joinToString(", "),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    productUI.quantity?.let { quantity ->
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = quantity,
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.semantics {
                                    contentDescription = "Quantity: $quantity"
                                }
                            )
                            if (productUI.additivesCount != "N/A") {
                                Spacer(modifier = Modifier.width(8.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(Color.Transparent)
                                        .border(
                                            width = 1.dp,
                                            color = MaterialTheme.colorScheme.outline,
                                            shape = RoundedCornerShape(4.dp)
                                        )
                                        .padding(horizontal = 6.dp, vertical = 2.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "${productUI.additivesCount} Additives",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        fontWeight = FontWeight.Medium,
                                        modifier = Modifier.semantics {
                                            contentDescription =
                                                "Additives: ${productUI.additivesCount}"
                                        }
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Barcode: ${productUI.code}",
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    if (productUI.allergensTags.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Contains: ${productUI.allergensTags.joinToString(", ")}",
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                            color = MaterialTheme.colorScheme.error,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.semantics {
                                contentDescription =
                                    "Allergens: ${productUI.allergensTags.joinToString(", ")}"
                            }
                        )
                    }

                    if (productUI.ecoScoreGrade != "N/A") {
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "EcoScore: ${productUI.ecoScoreGrade}",
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            if (productUI.ecoScoreScore != "N/A") {
                                Spacer(modifier = Modifier.width(8.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(Color.Transparent)
                                        .border(
                                            width = 1.dp,
                                            color = MaterialTheme.colorScheme.outline,
                                            shape = RoundedCornerShape(4.dp)
                                        )
                                        .padding(horizontal = 6.dp, vertical = 2.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = productUI.ecoScoreScore,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    if (productUI.nutritionGrade != "N/A") {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
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
                                .padding(4.dp)
                                .semantics {
                                    contentDescription =
                                        "Nutrition grade: ${productUI.nutritionGrade}"
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = productUI.nutritionGrade,
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }

                    if (productUI.nutritionScore != "N/A") {
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color.Transparent)
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.outline,
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                                .semantics {
                                    contentDescription =
                                        "Nutrition score: ${productUI.nutritionScore}"
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = productUI.nutritionScore,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Grade E - Nutella")
@Composable
private fun ProductListItemPreviewE() {
    KnowYourIngredientsTheme {
        ProductListItem(
            productUI = ProductUI.fromDomain(productPreview),
            onClick = { /* No-op */ }
        )
    }
}

@Preview(showBackground = true, name = "Grade A with EcoScore and Image")
@Composable
private fun ProductListItemPreviewA() {
    KnowYourIngredientsTheme {
        ProductListItem(
            productUI = ProductUI.fromDomain(productPreview),
            onClick = { /* No-op */ }
        )
    }
}

@Preview(showBackground = true, name = "No Nutrition Data with Failed Image")
@Composable
private fun ProductListItemPreviewNoNutrition() {
    KnowYourIngredientsTheme {
        ProductListItem(
            productUI = ProductUI.fromDomain(
                productPreview.copy(
                    nutritionGrade = "A",
                    nutritionScore = 45,
                    additivesCount = 0,
                    additivesTags = emptyList(),
                    quantity = null,
                    frontThumbUrl = "https://invalid-url.com/image.jpg"
                )
            ),
            onClick = { /* No-op */ }
        )
    }
}