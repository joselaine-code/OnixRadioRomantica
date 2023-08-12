package br.com.onixradio.romantica.presentation.screens
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.onixradio.romantica.presentation.composables.CardSlider
import br.com.onixradio.romantica.presentation.composables.PlayingNow
import br.com.onixradio.romantica.presentation.composables.WelcomeToApp

@Composable
fun Home(title: String) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        WelcomeToApp(Modifier)
        PlayingNow(title)
        CardSlider()
    }
}