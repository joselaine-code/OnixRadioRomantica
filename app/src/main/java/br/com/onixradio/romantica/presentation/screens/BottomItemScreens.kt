package br.com.onixradio.romantica.presentation.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomItemScreens(val route: String, var label: String, val icon: ImageVector) {
    object Home : BottomItemScreens(ROUTE_HOME, "Home", Icons.Default.Home)
    object AboutUs : BottomItemScreens(ROUTE_ABOUT_US, "Sobre nós", Icons.Default.Person)
}

const val ROUTE_HOME = "home"
const val ROUTE_ABOUT_US = "about_us"
