package com.kyi.knowyouringredients.core.navigation.models

import androidx.compose.ui.graphics.vector.ImageVector
import com.kyi.knowyouringredients.core.navigation.MainScreenDestination

// Data class for navigation items
data class NavItem(
    val destination: MainScreenDestination,
    val label: String,
    val icon: ImageVector
)
