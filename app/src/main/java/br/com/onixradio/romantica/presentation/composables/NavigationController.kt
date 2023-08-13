package br.com.onixradio.romantica.presentation.composables

import android.content.Intent
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.FabPosition
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.com.onixradio.romantica.data.service.MediaService
import br.com.onixradio.romantica.presentation.screens.BottomItemScreens
import br.com.onixradio.romantica.presentation.screens.ScreenController
import br.com.onixradio.romantica.presentation.ui.theme.Background
import br.com.onixradio.romantica.presentation.ui.theme.BottomNavigationColor
import br.com.onixradio.romantica.presentation.ui.theme.OnixRadioRomanticaTheme
import br.com.onixradio.romantica.presentation.viewmodels.MainViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun NavigationController() {
    val viewModel: MainViewModel = hiltViewModel()
    val isPlaying by viewModel.isPlaying.collectAsState(false)
    val navController = rememberNavController()
    val items = listOf(
            BottomItemScreens.Home,
            BottomItemScreens.AboutUs
    )

    val localContext = LocalContext.current
    val intent = Intent(localContext, MediaService::class.java)

    val systemUiController = rememberSystemUiController()
    val useDarkIcons = MaterialTheme.colors.isLight
    SideEffect {
        systemUiController.setSystemBarsColor(
                color = Color.DarkGray, darkIcons = useDarkIcons
        )
    }

    Scaffold(
            backgroundColor = Background,
            bottomBar = {
                BottomNavigation(
                        backgroundColor = BottomNavigationColor,
                        modifier = Modifier
                                .height(70.dp)
                                .clip(shape = RoundedCornerShape(10.dp)),
                        elevation = 16.dp
                ) {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route

                    items.forEach { screen ->
                        BottomNavigationItem(
                                icon = {
                                    Icon(
                                            imageVector = screen.icon,
                                            contentDescription = screen.label,
                                            tint = if (currentRoute == screen.route) Color.White else Color.LightGray,
                                            modifier = Modifier.size(30.dp)
                                    )
                                },
                                selected = currentRoute == screen.route,
                                label = {
                                    Text(
                                            text = screen.label,
                                            color = if (currentRoute == screen.route) Color.White else Color.LightGray,
                                            textAlign = TextAlign.Center,
                                            fontSize = 12.sp
                                    )
                                },
                                onClick = {
                                    if (currentRoute != screen.route) {
                                        navController.graph.startDestinationRoute?.let { startRoute ->
                                            navController.popBackStack(startRoute, false)
                                        }
                                        navController.navigate(screen.route) {
                                            launchSingleTop = true
                                        }
                                    }
                                },
                                alwaysShowLabel = false,
                                selectedContentColor = Color.White
                        )
                    }
                }
            },
            floatingActionButtonPosition = FabPosition.Center,
            isFloatingActionButtonDocked = true,
            floatingActionButton = {
                SetFabButton(
                        isAudioPlaying = isPlaying,
                        onClick = {
                            if (isPlaying) {
                                viewModel.pausePlayer()
                            } else {
                                localContext.startService(intent)
                                viewModel.preparePlayer()
                                viewModel.getCurrentMusic()
                            }
                        }
                )
            }
    ) { innerPadding ->
        ScreenController(
                navController = navController,
                modifier = Modifier.padding(innerPadding),
                nowPlayingState = viewModel.nowPlaying
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    OnixRadioRomanticaTheme {
        NavigationController()
    }
}