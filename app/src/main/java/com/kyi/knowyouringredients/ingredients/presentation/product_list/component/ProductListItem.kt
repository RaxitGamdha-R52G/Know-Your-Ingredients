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
import com.kyi.knowyouringredients.ingredients.presentation.components.NutritionGrade
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
            .padding(horizontal = 8.dp, vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProductImage(
                imageUrl = productUI.imageUrl,
                size = 64.dp,
                contentDescription = productUI.productName
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = productUI.productName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = productUI.brands.joinToString(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Barcode: ${productUI.code}",
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                    color = MaterialTheme.colorScheme.outline
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            if (productUI.nutritionGrade != "N/A") {

                NutritionGrade(
                    productUI = productUI,
                    size = 36.dp,
                    style = MaterialTheme.typography.labelMedium
                )
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