package br.com.onixradio.romantica.presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import br.com.onixradio.romantica.presentation.ui.theme.boldStyle
import br.com.onixradio.romantica.presentation.ui.theme.mediumStyle

@Composable
fun PlayingNow(
    title: String
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(visible = title.isNotBlank()) {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = boldStyle) {
                        append("Tocando agora: ")
                    }
                    withStyle(style = mediumStyle) {
                        append(title)
                    }
                },
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}