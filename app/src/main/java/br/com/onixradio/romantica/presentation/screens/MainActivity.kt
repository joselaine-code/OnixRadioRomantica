package br.com.onixradio.romantica.presentation.screens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.com.onixradio.romantica.data.service.MediaService
import br.com.onixradio.romantica.presentation.screens.Screens.*
import br.com.onixradio.romantica.presentation.ui.theme.Background
import br.com.onixradio.romantica.presentation.ui.theme.BottomNavigationColor
import br.com.onixradio.romantica.presentation.ui.theme.OnixRadioRomanticaTheme
import br.com.onixradio.romantica.presentation.viewmodels.MainViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        val intent = Intent(applicationContext, MediaService::class.java)
        applicationContext.startService(intent)


        setContent {
            NavigationController()
        }
    }
}

@Composable
fun NavigationController() {

    val viewModel: MainViewModel = hiltViewModel()

    val isPlaying by viewModel.isPlaying.collectAsState(false)


    val navController = rememberNavController()
    val items = listOf(
        Home, AboutUs
    )

    val systemUiController = rememberSystemUiController()
    val useDarkIcons = MaterialTheme.colors.isLight

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.DarkGray, darkIcons = useDarkIcons
        )
    }

    Scaffold(backgroundColor = Background,
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
                items.forEach {
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                imageVector = it.icon,
                                contentDescription = it.label,
                                tint = if (currentRoute == it.route) Color.White
                                else Color.LightGray,
                                modifier = Modifier.size(30.dp)
                            )
                        },
                        selected = currentRoute == it.route,
                        label = {
                            Text(
                                text = it.label, color = if (currentRoute == it.route) Color.White
                                else Color.LightGray, textAlign = TextAlign.Center, fontSize = 12.sp
                            )
                        },
                        onClick = {
                            if (currentRoute != it.route) {
                                navController.graph.startDestinationRoute?.let { item ->
                                    navController.popBackStack(
                                        item, false
                                    )
                                }
                            }
                            if (currentRoute != it.route) {
                                navController.navigate(it.route) {
                                    launchSingleTop = true
                                }
                            }
                        },
                        alwaysShowLabel = false,
                        selectedContentColor = Color.White,
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            SetFabButton(isAudioPlaying = isPlaying, onClick = {
                if (isPlaying) {
                    viewModel.pausePlayer()
                } else {
                    viewModel.preparePlayer()
                    viewModel.getCurrentMusic()
                }
            })
        }) {
        ScreenController(navController = navController, modifier = Modifier.padding(it))
    }
}

@Composable
private fun SetFabButton(
    isAudioPlaying: Boolean,
    onClick: () -> Unit,
) {
    FloatingActionButton(
        shape = CircleShape,
        onClick = { onClick() },
        backgroundColor = Color.Red,
        contentColor = Color.White,
        modifier = Modifier.size(60.dp)
    ) {
        Icon(
            imageVector = if (isAudioPlaying) Icons.Default.Clear
            else Icons.Default.PlayArrow, contentDescription = "Play"
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