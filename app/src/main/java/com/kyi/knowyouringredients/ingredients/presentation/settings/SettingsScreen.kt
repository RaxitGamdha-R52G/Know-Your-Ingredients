package com.kyi.knowyouringredients.ingredients.presentation.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudQueue
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kyi.knowyouringredients.R
import com.kyi.knowyouringredients.ingredients.presentation.settings.components.SettingsCard
import com.kyi.knowyouringredients.ingredients.presentation.settings.components.SettingsCardHeaderWithIcon
import com.kyi.knowyouringredients.ingredients.presentation.settings.components.SettingsNote
import com.kyi.knowyouringredients.ingredients.presentation.settings.components.SettingsRadioRow
import com.kyi.knowyouringredients.ingredients.presentation.settings.components.SettingsToggleRow
import com.kyi.knowyouringredients.ui.theme.KnowYourIngredientsTheme

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