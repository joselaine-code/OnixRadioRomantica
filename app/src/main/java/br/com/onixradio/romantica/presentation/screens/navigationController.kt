package br.com.onixradio.romantica.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.onixradio.romantica.AboutUs

@Composable
fun ScreenController(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = ROUTE_HOME) {
        composable(ROUTE_HOME) {
            Home()
        }
        composable(ROUTE_NEWS) {
            News()
        }
        composable(ROUTE_ABOUT_US) {
            AboutUs()
        }
    }
}