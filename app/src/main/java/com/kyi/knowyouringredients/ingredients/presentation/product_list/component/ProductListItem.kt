package com.kyi.knowyouringredients.ingredients.presentation.product_list.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
                                modifier = Modifier.semantics { contentDescription = "Quantity: $quantity" }
                            )
                            productUI.additivesInfo?.count?.let { count ->
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
                                        text = "$count Additives",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        fontWeight = FontWeight.Medium,
                                        modifier = Modifier.semantics { contentDescription = "Additives: $count" }
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

                    productUI.allergensInfo?.tags?.takeIf { it.isNotEmpty() }?.let { allergens ->
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Contains: ${allergens.joinToString(", ")}",
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                            color = MaterialTheme.colorScheme.error,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.semantics { contentDescription = "Allergens: ${allergens.joinToString(", ")}" }
                        )
                    }
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    productUI.nutritionInfo?.grade?.let { grade ->
                        Box(
                            modifier = Modifier
                                .size(40.dp)
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
                                .padding(4.dp)
                                .semantics { contentDescription = "Nutrition grade: $grade" },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = grade.uppercase(),
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }

                    productUI.nutritionInfo?.score?.let { score ->
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
                                .semantics { contentDescription = "Nutrition score: $score" },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = score,
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

@Preview(showBackground = true, name = "Grade A")
@Composable
private fun ProductListItemPreviewA() {
    KnowYourIngredientsTheme {
        ProductListItem(
            productUI = ProductUI.fromDomain(
                productPreview.copy(nutritionInfo = productPreview.nutritionInfo?.copy(grade = "A", score = 5))
            ),
            onClick = { /* No-op */ }
        )
    }
}

@Preview(showBackground = true, name = "No Nutrition Data")
@Composable
private fun ProductListItemPreviewNoNutrition() {
    KnowYourIngredientsTheme {
        ProductListItem(
            productUI = ProductUI.fromDomain(
                productPreview.copy(nutritionInfo = null, additivesInfo = null, quantity = null)
            ),
            onClick = { /* No-op */ }
        )
    }
}


//package com.kyi.knowyouringredients.ingredients.presentation.product_list.component
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.draw.drawBehind
//import androidx.compose.ui.geometry.Offset
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextOverflow
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.kyi.knowyouringredients.ingredients.presentation.models.ProductUI
//import com.kyi.knowyouringredients.ingredients.presentation.productPreview
//import com.kyi.knowyouringredients.ui.theme.KnowYourIngredientsTheme
//
//@Composable
//fun ProductListItem(
//    productUI: ProductUI,
//    onClick: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    // Define colors outside drawBehind
//    val primaryColor = Color.Green
//    val secondaryColor = Color.Cyan
//
//    Card(
//        modifier = modifier
//            .fillMaxWidth()
//            .clickable(onClick = onClick)
//            .padding(horizontal = 8.dp, vertical = 4.dp),
//        shape = RoundedCornerShape(16.dp),
//        elevation = CardDefaults.cardElevation(
//            defaultElevation = 4.dp,
//            pressedElevation = 8.dp,
//            focusedElevation = 8.dp
//        ),
//        colors = CardDefaults.cardColors(
//            containerColor = MaterialTheme.colorScheme.surface
//        )
//    ) {
//        Box(
//            modifier = Modifier
//                .background(
//                    brush = Brush.linearGradient(
//                        colors = listOf(
//                            MaterialTheme.colorScheme.surface,
//                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
//                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f)
//                        ),
//                        start = Offset(0f, 0f),
//                        end = Offset(1000f, 1000f)
//                    )
//                )
//                .drawBehind {
//                    // Subtle artistic wave pattern using predefined colors
//                    drawCircle(
//                        color = primaryColor.copy(alpha = 0.3f),
//                        radius = size.width * 0.8f,
//                        center = Offset(size.width * 0.9f, size.height * 0.2f)
//                    )
//                    drawCircle(
//                        color = secondaryColor.copy(alpha = 0.3f),
//                        radius = size.width * 0.6f,
//                        center = Offset(size.width * 0.1f, size.height * 0.8f)
//                    )
//                }
//                .padding(16.dp)
//        ) {
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                // Main content
//                Column(
//                    modifier = Modifier.weight(1f)
//                ) {
//                    // Product name
//                    Text(
//                        text = productUI.productName,
//                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 22.sp),
//                        fontWeight = FontWeight.Bold,
//                        color = MaterialTheme.colorScheme.primary,
//                        maxLines = 1,
//                        overflow = TextOverflow.Ellipsis
//                    )
//                    Spacer(modifier = Modifier.size(8.dp))
//
//                    // Brands with "Company" tag
//                    Row(verticalAlignment = Alignment.CenterVertically) {
//                        Box(
//                            modifier = Modifier
//                                .size(100.dp, 30.dp)
//                                .clip(RoundedCornerShape(6.dp))
//                                .background(Color.Transparent)
//                                .padding(horizontal = 8.dp, vertical = 4.dp)
//                                .border(
//                                    width = 1.dp,
//                                    color = MaterialTheme.colorScheme.outline,
//                                    shape = RoundedCornerShape(5.dp)
//                                ),
//                            contentAlignment = Alignment.Center
//                        ) {
//                            Text(
//                                text = when (productUI.brands.size) {
//                                    1 -> "Company"
//                                    else -> "Companies"
//                                },
//                                style = MaterialTheme.typography.labelMedium,
//                                color = MaterialTheme.colorScheme.onSecondaryContainer,
//                                fontWeight = FontWeight.Medium,
//                            )
//                        }
//                        Spacer(modifier = Modifier.width(8.dp))
//                        Text(
//                            text = productUI.brands.joinToString(", "),
//                            style = MaterialTheme.typography.bodyMedium,
//                            color = MaterialTheme.colorScheme.onSurfaceVariant,
//                            maxLines = 1,
//                            overflow = TextOverflow.Ellipsis
//                        )
//                    }
//                    Spacer(modifier = Modifier.size(8.dp))
//
//                    // Barcode
//                    Text(
//                        text = productUI.code,
//                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
//                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
//                        maxLines = 1,
//                        overflow = TextOverflow.Ellipsis
//                    )
//                }
//
//                // Nutrition info (grade and score)
//                Column(
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    // Nutrition grade in a circle with stroke
//                    productUI.nutritionGrade?.let { grade ->
//                        Box(
//                            modifier = Modifier
//                                .size(40.dp)
//                                .clip(CircleShape)
//                                .background(
//                                    color = when (grade.lowercase()) {
//                                        "a" -> Color(0xFF00C853) // Green for A
//                                        "b" -> Color(0xFF76FF03) // Light Green for B
//                                        "c" -> Color(0xFFFFC107) // Amber for C
//                                        "d" -> Color(0xFFFF5722) // Orange for D
//                                        "e" -> Color(0xFFFF0000) // Red for E
//                                        else -> MaterialTheme.colorScheme.onSurfaceVariant
//                                    }
//                                )
//                                .border(
//                                    width = 2.dp,
//                                    color = MaterialTheme.colorScheme.outline,
//                                    shape = CircleShape
//                                )
//                                .padding(4.dp),
//                            contentAlignment = Alignment.Center
//                        ) {
//                            Text(
//                                text = grade.uppercase(),
//                                style = MaterialTheme.typography.labelLarge,
//                                fontWeight = FontWeight.Bold,
//                                color = MaterialTheme.colorScheme.onPrimary
//                            )
//                        }
//                    }
//
//                    // Nutrition score below grade
//                    productUI.nutritionScore?.let { score ->
//                        Spacer(modifier = Modifier.size(10.dp))
//                        Box(
//                            modifier = Modifier
//                                .size(50.dp, 30.dp)
//                                .clip(RoundedCornerShape(4.dp))
//                                .background(Color.Transparent)
//                                .padding(horizontal = 6.dp, vertical = 2.dp)
//                                .border(
//                                    width = 1.dp,
//                                    color = MaterialTheme.colorScheme.outline,
//                                    shape = RoundedCornerShape(5.dp)
//                                ),
//                            contentAlignment = Alignment.Center
//                        ) {
//                            Text(
//                                text = score.toString(),
//                                style = MaterialTheme.typography.labelSmall,
//                                color = MaterialTheme.colorScheme.onPrimaryContainer,
//                                fontWeight = FontWeight.Medium,
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Preview(showBackground = true, name = "Grade A")
//@Composable
//private fun ProductListItemPreviewA() {
//    KnowYourIngredientsTheme {
//        ProductListItem(
//            productUI = ProductUI.fromDomain(productPreview.copy(nutritionGrade = "A")),
//            onClick = { /* No-op for preview */ }
//        )
//    }
//}
//
//@Preview(showBackground = true, name = "Grade B")
//@Composable
//private fun ProductListItemPreviewB() {
//    KnowYourIngredientsTheme {
//        ProductListItem(
//            productUI = ProductUI.fromDomain(productPreview.copy(nutritionGrade = "B")),
//            onClick = { /* No-op for preview */ }
//        )
//    }
//}
//
//@Preview(showBackground = true, name = "Grade C")
//@Composable
//private fun ProductListItemPreviewC() {
//    KnowYourIngredientsTheme {
//        ProductListItem(
//            productUI = ProductUI.fromDomain(productPreview.copy(nutritionGrade = "C")),
//            onClick = { /* No-op for preview */ }
//        )
//    }
//}
//
//@Preview(showBackground = true, name = "Grade D")
//@Composable
//private fun ProductListItemPreviewD() {
//    KnowYourIngredientsTheme {
//        ProductListItem(
//            productUI = ProductUI.fromDomain(productPreview.copy(nutritionGrade = "D")),
//            onClick = { /* No-op for preview */ }
//        )
//    }
//}
//
//@Preview(showBackground = true, name = "Grade E")
//@Composable
//private fun ProductListItemPreviewE() {
//    KnowYourIngredientsTheme {
//        ProductListItem(
//            productUI = ProductUI.fromDomain(productPreview.copy(nutritionGrade = "E")),
//            onClick = { /* No-op for preview */ }
//        )
//    }
//}
//
