package com.kyi.knowyouringredients.ingredients.presentation.product_detail.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun InfoText(@androidx.annotation.StringRes label: Int, value100g: String, valueServing: String) {
    Text(text = stringResource(label, value100g, valueServing), style = MaterialTheme.typography.bodyMedium)
}
