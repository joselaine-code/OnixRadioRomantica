package br.com.onixradio.romantica.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.flow.StateFlow

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object AboutUs : Screen("about_us")
}

@Composable
fun ScreenController(
    modifier: Modifier = Modifier,
    nowPlayingState: StateFlow<String>,
    navController: NavHostController
) {
    val state = nowPlayingState.collectAsState()

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            Home(state.value)
        }
        composable(Screen.AboutUs.route) {
            AboutUs()
        }
    }
}
