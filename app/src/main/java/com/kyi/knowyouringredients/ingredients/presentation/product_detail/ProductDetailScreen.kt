package com.kyi.knowyouringredients.ingredients.presentation.product_detail

import android.util.Log
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.unit.dp
import com.kyi.knowyouringredients.R
import com.kyi.knowyouringredients.ingredients.presentation.components.NutritionGrade
import com.kyi.knowyouringredients.ingredients.presentation.components.ProductImage
import com.kyi.knowyouringredients.ingredients.presentation.models.ProductUI
import com.kyi.knowyouringredients.ingredients.presentation.product_detail.components.DetailCard
import com.kyi.knowyouringredients.ingredients.presentation.product_detail.components.IngredientsTable
import com.kyi.knowyouringredients.ingredients.presentation.product_detail.components.NutrientsTable
import com.kyi.knowyouringredients.ingredients.presentation.product_list.ProductListState
import com.kyi.knowyouringredients.ui.theme.KnowYourIngredientsTheme

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
    val isLoading = when (state) {
        is ProductListState.Search -> state.state.isLoading
        is ProductListState.Scan -> state.state.isLoading
    }

    BackHandler(enabled = !isNavigatingBack, onBack = {
        isNavigatingBack = true
        onBack()
    })

    if (isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (selectedProduct != null && !isNavigatingBack) {
        val productUI = selectedProduct
        Log.d("ProductDetailScreen", "Rendering product: ${productUI.productName}, ingredients count: ${productUI.ingredients.size}")
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
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
                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = stringResource(R.string.ingredients),
                            style = MaterialTheme.typography.titleLarge
                        )
                        IngredientsTable(ingredients = productUI.ingredients)
                    }
                }
            }

            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = stringResource(R.string.nutrition_per_100g_serving),
                            style = MaterialTheme.typography.titleLarge
                        )
                        NutrientsTable(product = productUI)
                    }
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
                            ),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    if (productUI.allergensTags.isNotEmpty())
                        Text(
                            text = stringResource(
                                R.string.allergens,
                                productUI.allergensTags.joinToString()
                            ),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    if (productUI.categoriesTags.isNotEmpty())
                        Text(
                            text = stringResource(
                                R.string.categories,
                                productUI.categoriesTags.joinToString()
                            ),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    if (productUI.labelsTags.isNotEmpty())
                        Text(
                            text = stringResource(
                                R.string.labels,
                                productUI.labelsTags.joinToString()
                            ),
                            style = MaterialTheme.typography.bodyMedium
                        )
                }
            }

            item {
                Button(
                    onClick = {
                        isNavigatingBack = true
                        onBack()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(stringResource(R.string.back))
                }
            }
        }
    } else if (!isNavigatingBack) {
        Box(
            modifier = modifier.fillMaxSize(),
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