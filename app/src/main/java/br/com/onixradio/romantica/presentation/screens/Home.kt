package br.com.onixradio.romantica.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.onixradio.romantica.presentation.composables.AudioPlayer
import br.com.onixradio.romantica.presentation.composables.CardSlider
import br.com.onixradio.romantica.presentation.composables.PlayingNow
import br.com.onixradio.romantica.presentation.composables.WelcomeToApp
import br.com.onixradio.romantica.presentation.viewmodels.MainViewModel

@Composable
fun Home() {
    val scrollState = rememberScrollState()
    val viewModel: MainViewModel =  hiltViewModel()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        viewModel.musicNowPlaying()
        val actualMusic = rememberSaveable { mutableStateOf("") }
        WelcomeToApp()
        PlayingNow(actualMusic.value)
        CardSlider()
    }
}