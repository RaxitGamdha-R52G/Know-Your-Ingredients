package com.kyi.knowyouringredients.ingredients.presentation.product_detail.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.kyi.knowyouringredients.ingredients.presentation.models.ProductUI
import com.kyi.knowyouringredients.ingredients.presentation.productPreview
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.tooling.preview.Preview
import kotlin.math.roundToInt

data class NutrientRow(val name: String, val per100g: String, val perServing: String, val dvPercent: String)

@Composable
fun NutrientsTable(product: ProductUI) {
    val nutrients = getNutrientRows(product)
    var selectedOption by remember { mutableStateOf("Per 100g") }
    var isDropdownExpanded by remember { mutableStateOf(false) }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Nutrient",
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
            Box(
                modifier = Modifier
                    .weight(1.2f),
                contentAlignment = Alignment.Center
            ) {
                TextButton(
                    onClick = { isDropdownExpanded = true },
                    modifier = Modifier.padding(start = 4.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = selectedOption,
                            style = MaterialTheme.typography.labelLarge,
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
                        text = { Text("Per 100g") },
                        onClick = {
                            selectedOption = "Per 100g"
                            isDropdownExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Per Serving") },
                        onClick = {
                            selectedOption = "Per Serving"
                            isDropdownExpanded = false
                        }
                    )
                }
            }
            Text(
                text = "%DV",
                modifier = Modifier
                    .weight(0.8f)
                    .padding(start = 4.dp),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
        }
        HorizontalDivider(
            Modifier,
            DividerDefaults.Thickness,
            color = MaterialTheme.colorScheme.outline
        )

        nutrients.forEach { row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(0.5.dp, MaterialTheme.colorScheme.outline)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = row.name,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 4.dp),
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = if (selectedOption == "Per 100g") row.per100g else row.perServing,
                    modifier = Modifier
                        .weight(1.2f)
                        .padding(start = 4.dp),
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    maxLines = 1 // Single-line for data
                )
                Text(
                    text = row.dvPercent,
                    modifier = Modifier
                        .weight(0.8f)
                        .padding(start = 4.dp),
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    maxLines = 1 // Single-line for data
                )
            }
        }
    }
}

private fun getNutrientRows(product: ProductUI): List<NutrientRow> {
    val sodiumConversionFactor = 393.0 // mg sodium per g salt

    fun String.toNutrientValue(): Double? {
        return try {
            this.replace(Regex("[^0-9.]"), "").toDoubleOrNull()
        } catch (_: Exception) {
            null
        }
    }

    return listOf(
        NutrientRow(
            name = "Energy",
            per100g = product.energyKcal100g,
            perServing = product.energyKcalServing,
            dvPercent = product.energyKcalServing.toNutrientValue()?.let { "${(it / 2000 * 100).roundToInt()}%" } ?: "-"
        ),
        NutrientRow(
            name = "Fat",
            per100g = product.fat100g,
            perServing = product.fatServing,
            dvPercent = product.fatServing.toNutrientValue()?.let { "${(it / 78 * 100).roundToInt()}%" } ?: "-"
        ),
        NutrientRow(
            name = "Saturated Fat",
            per100g = product.saturatedFat100g,
            perServing = product.saturatedFatServing,
            dvPercent = product.saturatedFatServing.toNutrientValue()?.let { "${(it / 20 * 100).roundToInt()}%" } ?: "-"
        ),
        NutrientRow(
            name = "Carbohydrates",
            per100g = product.carbohydrates100g,
            perServing = product.carbohydratesServing,
            dvPercent = product.carbohydratesServing.toNutrientValue()?.let { "${(it / 275 * 100).roundToInt()}%" } ?: "-"
        ),
        NutrientRow(
            name = "Sugars",
            per100g = product.sugars100g,
            perServing = product.sugarsServing,
            dvPercent = "-" // No standard DV for total sugars
        ),
        NutrientRow(
            name = "Proteins",
            per100g = product.proteins100g,
            perServing = product.proteinsServing,
            dvPercent = product.proteinsServing.toNutrientValue()?.let { "${(it / 50 * 100).roundToInt()}%" } ?: "-"
        ),
        NutrientRow(
            name = "Salt",
            per100g = product.salt100g,
            perServing = product.saltServing,
            dvPercent = product.saltServing.toNutrientValue()?.let {
                val sodiumServing = it * sodiumConversionFactor
                "${(sodiumServing / 2300 * 100).roundToInt()}%"
            } ?: "-"
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun NutrientsTablePreview() {
    NutrientsTable(ProductUI.fromDomain(productPreview))
}