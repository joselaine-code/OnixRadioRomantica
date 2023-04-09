package br.com.onixradio.romantica.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.onixradio.romantica.AboutUs

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object AboutUs : Screen("about_us")
}

@Composable
fun ScreenController(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            Home()
        }
        composable(Screen.AboutUs.route) {
            AboutUs()
        }
    }
}
