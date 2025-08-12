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
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.kyi.knowyouringredients.R
import com.kyi.knowyouringredients.core.navigation.AuthDestination
import com.kyi.knowyouringredients.ingredients.data.preferences.PreferenceDataSource
import com.kyi.knowyouringredients.ingredients.presentation.settings.components.SettingsCard
import com.kyi.knowyouringredients.ingredients.presentation.settings.components.SettingsCardHeaderWithIcon
import com.kyi.knowyouringredients.ingredients.presentation.settings.components.SettingsNote
import com.kyi.knowyouringredients.ingredients.presentation.settings.components.SettingsRadioRow
import com.kyi.knowyouringredients.ingredients.presentation.settings.components.SettingsToggleRow
import com.kyi.knowyouringredients.ui.theme.KnowYourIngredientsTheme
import com.kyi.knowyouringredients.ingredients.presentation.viewmodels.AuthViewModel
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.koin.androidx.compose.koinViewModel
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues = PaddingValues(0.dp),
    navController: NavHostController,
    authViewModel: AuthViewModel = koinViewModel() // Inject AuthViewModel
) {
    val preferenceDataSource: PreferenceDataSource = koinInject()
    val isDarkMode = preferenceDataSource.darkModeFlow.collectAsState(initial = false).value
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
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
                SettingsToggleRow(
                    title = stringResource(R.string.night_mode),
                    checked = isDarkMode,
                    onCheckedChange = { newValue ->
                        coroutineScope.launch {
                            preferenceDataSource.setDarkMode(newValue)
                        }
                    }
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
                val selectedSource = androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf("OpenFoodFacts") }
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
                Button(
                    onClick = {
                        FirebaseAuth.getInstance().signOut()
                        GoogleSignIn.getClient(
                            context,
                            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                .requestIdToken(context.getString(R.string.default_web_client_id))
                                .requestEmail()
                                .build()
                        ).signOut()
                        Toast.makeText(context, "Logged out successfully", Toast.LENGTH_SHORT).show()
                        authViewModel.clearAuthState()
                        navController.navigate(AuthDestination.Login.route) {
                            popUpTo(AuthDestination.Main.route) { inclusive = true }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    Text("Logout")
                }
            }
        }
    }
}

@Preview
@Composable
private fun SettingsScreenPreview() {
    KnowYourIngredientsTheme {
        SettingsScreen(navController = rememberNavController())
    }
}