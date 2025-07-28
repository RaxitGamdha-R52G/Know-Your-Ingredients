package com.kyi.knowyouringredients.ingredients.presentation.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudQueue
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kyi.knowyouringredients.R
import com.kyi.knowyouringredients.ingredients.presentation.settings.components.SettingsCard
import com.kyi.knowyouringredients.ingredients.presentation.settings.components.SettingsCardHeaderWithIcon
import com.kyi.knowyouringredients.ingredients.presentation.settings.components.SettingsNote
import com.kyi.knowyouringredients.ingredients.presentation.settings.components.SettingsRadioRow
import com.kyi.knowyouringredients.ingredients.presentation.settings.components.SettingsToggleRow
import com.kyi.knowyouringredients.ui.theme.KnowYourIngredientsTheme
//
//@Composable
//fun SettingsScreen(
//    modifier: Modifier = Modifier,
//    innerPadding: PaddingValues = PaddingValues(0.dp)
//) {
//    LazyColumn(
//        modifier = modifier
//            .fillMaxSize()
//            .padding(innerPadding)
//            .padding(16.dp),
//        verticalArrangement = Arrangement.spacedBy(16.dp)
//    ) {
//        item {
//            Card(
//                shape = RoundedCornerShape(12.dp),
//                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
//                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
//            ) {
//                Column(modifier = Modifier.padding(16.dp)) {
//                    Text(
//                        text = stringResource(R.string.settings_appearance),
//                        style = MaterialTheme.typography.titleLarge,
//                        fontWeight = FontWeight.SemiBold
//                    )
//                    Spacer(modifier = Modifier.height(8.dp))
//                    var isDarkMode by remember { mutableStateOf(false) }
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.SpaceBetween
//                    ) {
//                        Text(
//                            text = stringResource(R.string.night_mode),
//                            style = MaterialTheme.typography.bodyMedium
//                        )
//                        Switch(
//                            checked = isDarkMode,
//                            onCheckedChange = { isDarkMode = it }
//                        )
//                    }
//                    Text(
//                        text = stringResource(R.string.night_mode_note),
//                        style = MaterialTheme.typography.bodySmall,
//                        color = MaterialTheme.colorScheme.onSurfaceVariant,
//                        modifier = Modifier.padding(top = 8.dp)
//                    )
//                }
//            }
//        }
//
//        item {
//            Card(
//                shape = RoundedCornerShape(12.dp),
//                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
//                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
//            ) {
//                Column(modifier = Modifier.padding(16.dp)) {
//                    Text(
//                        text = stringResource(R.string.settings_data_source),
//                        style = MaterialTheme.typography.titleLarge,
//                        fontWeight = FontWeight.SemiBold
//                    )
//                    Spacer(modifier = Modifier.height(8.dp))
//                    var selectedSource by remember { mutableStateOf("OpenFoodFacts") }
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        RadioButton(
//                            selected = selectedSource == "OpenFoodFacts",
//                            onClick = { selectedSource = "OpenFoodFacts" },
//                            enabled = false
//                        )
//                        Text(
//                            text = stringResource(R.string.data_source_food),
//                            style = MaterialTheme.typography.bodyMedium,
//                            modifier = Modifier.padding(start = 8.dp)
//                        )
//                    }
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        RadioButton(
//                            selected = selectedSource == "OpenBeautyFacts",
//                            onClick = { selectedSource = "OpenBeautyFacts"},
//                            enabled = false
//                        )
//                        Text(
//                            text = stringResource(R.string.data_source_beauty),
//                            style = MaterialTheme.typography.bodyMedium,
//                            modifier = Modifier.padding(start = 8.dp)
//                        )
//                    }
//                    Text(
//                        text = stringResource(R.string.data_source_note),
//                        style = MaterialTheme.typography.bodySmall,
//                        color = MaterialTheme.colorScheme.onSurfaceVariant,
//                        modifier = Modifier.padding(top = 8.dp)
//                    )
//                }
//            }
//        }
//
//        item {
//            Card(
//                shape = RoundedCornerShape(12.dp),
//                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
//                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
//            ) {
//                Column(modifier = Modifier.padding(16.dp)) {
//                    Text(
//                        text = stringResource(R.string.settings_language),
//                        style = MaterialTheme.typography.titleLarge,
//                        fontWeight = FontWeight.SemiBold
//                    )
//                    Spacer(modifier = Modifier.height(8.dp))
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        RadioButton(
//                            selected = true,
//                            onClick = { /* English only for now */ },
//                            enabled = false
//                        )
//                        Text(
//                            text = stringResource(R.string.language_english),
//                            style = MaterialTheme.typography.bodyMedium,
//                            modifier = Modifier.padding(start = 8.dp)
//                        )
//                    }
//                    Text(
//                        text = stringResource(R.string.language_note),
//                        style = MaterialTheme.typography.bodySmall,
//                        color = MaterialTheme.colorScheme.onSurfaceVariant,
//                        modifier = Modifier.padding(top = 8.dp)
//                    )
//                }
//            }
//        }
//
//        item {
//            Card(
//                shape = RoundedCornerShape(12.dp),
//                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
//                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
//            ) {
//                Column(modifier = Modifier.padding(16.dp)) {
//                    Text(
//                        text = stringResource(R.string.settings_general),
//                        style = MaterialTheme.typography.titleLarge,
//                        fontWeight = FontWeight.SemiBold
//                    )
//                    Spacer(modifier = Modifier.height(8.dp))
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.SpaceBetween
//                    ) {
//                        Text(
//                            text = stringResource(R.string.clear_cache),
//                            style = MaterialTheme.typography.bodyMedium
//                        )
//                        Switch(
//                            checked = false,
//                            onCheckedChange = { /* Placeholder */ },
//                            enabled = false
//                        )
//                    }
//                    Text(
//                        text = stringResource(R.string.clear_cache_note),
//                        style = MaterialTheme.typography.bodySmall,
//                        color = MaterialTheme.colorScheme.onSurfaceVariant,
//                        modifier = Modifier.padding(top = 8.dp)
//                    )
//                }
//            }
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//private fun SettingsScreenPreview() {
//    KnowYourIngredientsTheme {
//        SettingsScreen()
//    }
//}


//@Composable
//fun SettingsScreen(
//    modifier: Modifier = Modifier,
//    innerPadding: PaddingValues = PaddingValues(0.dp)
//) {
//    LazyColumn(
//        modifier = modifier
//            .fillMaxSize()
//            .padding(innerPadding)
//            .padding(horizontal = 16.dp, vertical = 8.dp),
//        verticalArrangement = Arrangement.spacedBy(12.dp)
//    ) {
//        item {
//            SettingsCard(title = stringResource(R.string.settings_appearance)) {
//                val isDarkMode = remember { mutableStateOf(false) }
//                SettingsToggleRow(
//                    title = stringResource(R.string.night_mode),
//                    checked = isDarkMode.value,
//                    onCheckedChange = { isDarkMode.value = it }
//                )
//                SettingsNote(stringResource(R.string.night_mode_note))
//            }
//        }
//
//        item {
//
//            SettingsCard(title = stringResource(R.string.settings_data_source)) {
//                val selectedSource = remember { mutableStateOf("OpenFoodFacts") }
//                SettingsRadioRow(
//                    label = stringResource(R.string.data_source_food),
//                    selected = selectedSource.value == "OpenFoodFacts",
//                    onClick = { selectedSource.value = "OpenFoodFacts" },
//                    enabled = false
//                )
//                SettingsRadioRow(
//                    label = stringResource(R.string.data_source_beauty),
//                    selected = selectedSource.value == "OpenBeautyFacts",
//                    onClick = { selectedSource.value = "OpenBeautyFacts" },
//                    enabled = false
//                )
//                SettingsNote(stringResource(R.string.data_source_note))
//            }
//        }
//
//        item {
//            SettingsCard(title = stringResource(R.string.settings_language)) {
//                SettingsRadioRow(
//                    label = stringResource(R.string.language_english),
//                    selected = true,
//                    onClick = { }
//                )
//                SettingsNote(stringResource(R.string.language_note))
//            }
//        }
//
//        item {
//            SettingsCard(title = stringResource(R.string.settings_general)) {
//                SettingsToggleRow(
//                    title = stringResource(R.string.clear_cache),
//                    checked = false,
//                    onCheckedChange = { },
//                    enabled = false
//                )
//                SettingsNote(stringResource(R.string.clear_cache_note))
//            }
//        }
//    }
//}


@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            SettingsCard(
                headerContent = {
                    SettingsCardHeaderWithIcon(
                        title = stringResource(R.string.settings_appearance),
                        icon = Icons.Default.DarkMode
                    )
                }
            ) {
                val isDarkMode = remember { mutableStateOf(false) }
                SettingsToggleRow(
                    title = stringResource(R.string.night_mode),
                    checked = isDarkMode.value,
                    onCheckedChange = { isDarkMode.value = it }
                )
                SettingsNote(stringResource(R.string.night_mode_note))
            }
        }

        item {
            SettingsCard(
                headerContent = {
                    SettingsCardHeaderWithIcon(
                        title = stringResource(R.string.settings_data_source),
                        icon = Icons.Default.CloudQueue
                    )
                }
            ) {
                val selectedSource = remember { mutableStateOf("OpenFoodFacts") }
                SettingsRadioRow(
                    label = stringResource(R.string.data_source_food),
                    selected = selectedSource.value == "OpenFoodFacts",
                    onClick = { selectedSource.value = "OpenFoodFacts" },
                    enabled = false
                )
                SettingsRadioRow(
                    label = stringResource(R.string.data_source_beauty),
                    selected = selectedSource.value == "OpenBeautyFacts",
                    onClick = { selectedSource.value = "OpenBeautyFacts" },
                    enabled = false
                )
                SettingsNote(stringResource(R.string.data_source_note))
            }
        }

        item {
            SettingsCard(
                headerContent = {
                    SettingsCardHeaderWithIcon(
                        title = stringResource(R.string.settings_language),
                        icon = Icons.Default.Language
                    )
                }
            ) {
                SettingsRadioRow(
                    label = stringResource(R.string.language_english),
                    selected = true,
                    onClick = { }
                )
                SettingsNote(stringResource(R.string.language_note))
            }
        }

        item {
            SettingsCard(
                headerContent = {
                    SettingsCardHeaderWithIcon(
                        title = stringResource(R.string.settings_general),
                        icon = Icons.Default.Settings
                    )
                }
            ) {
                SettingsToggleRow(
                    title = stringResource(R.string.clear_cache),
                    checked = false,
                    onCheckedChange = { },
                    enabled = false
                )
                SettingsNote(stringResource(R.string.clear_cache_note))
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
private fun SettingsScreenPreview() {
    KnowYourIngredientsTheme {
        SettingsScreen()
    }
    
}