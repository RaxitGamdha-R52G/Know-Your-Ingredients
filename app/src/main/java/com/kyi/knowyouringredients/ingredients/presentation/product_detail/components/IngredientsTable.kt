package com.kyi.knowyouringredients.ingredients.presentation.product_detail.components

import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.kyi.knowyouringredients.ingredients.presentation.models.IngredientUI
import com.kyi.knowyouringredients.ingredients.presentation.models.ProductUI
import com.kyi.knowyouringredients.ingredients.presentation.productPreview
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun IngredientsTable(ingredients: List<IngredientUI>) {
    if (ingredients.isEmpty()) {
        Text(
            text = "No ingredients listed",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(8.dp)
        )
        return
    }

    var selectedOption by remember { mutableStateOf("Vegan") }
    var isDropdownExpanded by remember { mutableStateOf(false) }

    Column {
        // Header Row with Horizontal Scroll
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Name",
                modifier = Modifier
                    .weight(1.2f)
                    .padding(start = 4.dp),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
                // No maxLines, allowing multi-line
            )
            Box(
                modifier = Modifier
                    .weight(1.0f),
                contentAlignment = Alignment.Center
            ) {
                TextButton(
                    onClick = { isDropdownExpanded = true },
                    modifier = Modifier.padding(start = 2.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = selectedOption,
                            style = MaterialTheme.typography.titleSmall,
                            textAlign = TextAlign.Center,
                            maxLines = 1, // Prevent multi-line
                            overflow = TextOverflow.Ellipsis
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Dropdown",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                DropdownMenu(
                    expanded = isDropdownExpanded,
                    onDismissRequest = { isDropdownExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Vegan") },
                        onClick = {
                            selectedOption = "Vegan"
                            isDropdownExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Vegetarian") },
                        onClick = {
                            selectedOption = "Vegetarian"
                            isDropdownExpanded = false
                        }
                    )
                }
            }
            Text(
                text = "% Estimate",
                modifier = Modifier
                    .weight(1.0f)
                    .padding(start = 4.dp),
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center
                // No maxLines, allowing multi-line
            )
        }
        HorizontalDivider(
            Modifier,
            DividerDefaults.Thickness,
            color = MaterialTheme.colorScheme.outline
        )

        // Ingredient Rows
        ingredients.forEach { ingredient ->
            IngredientRow(ingredient, indent = 0, selectedOption = selectedOption)
            // Recursively render sub-ingredients
            ingredient.subIngredients.forEach { subIngredient ->
                IngredientRow(subIngredient, indent = 1, selectedOption = selectedOption)
                // Support deeper nesting
                subIngredient.subIngredients.forEach { subSubIngredient ->
                    IngredientRow(subSubIngredient, indent = 2, selectedOption = selectedOption)
                }
            }
        }
    }
}

@Composable
private fun IngredientRow(ingredient: IngredientUI, indent: Int, selectedOption: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(start = (indent * 16).dp)
            .border(0.5.dp, MaterialTheme.colorScheme.outline)
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = ingredient.name,
            modifier = Modifier
                .weight(1.2f)
                .padding(start = 4.dp),
            style = if (indent > 0) MaterialTheme.typography.bodySmall else MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Start
            // No maxLines, allowing multi-line
        )
        Text(
            text = if (selectedOption == "Vegan") ingredient.isVegan else ingredient.isVegetarian,
            modifier = Modifier
                .weight(1.0f)
                .padding(start = 4.dp),
            style = if (indent > 0) MaterialTheme.typography.bodySmall else MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            maxLines = 1 // Single-line for data
        )
        Text(
            text = ingredient.percent,
            modifier = Modifier
                .weight(1.0f)
                .padding(start = 4.dp),
            style = if (indent > 0) MaterialTheme.typography.bodySmall else MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            maxLines = 1 // Single-line for data
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun IngredientsTablePreview() {
    IngredientsTable(ProductUI.fromDomain(productPreview).ingredients)
}