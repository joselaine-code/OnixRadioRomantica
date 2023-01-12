package br.com.onixradio.romantica.presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.onixradio.romantica.presentation.ui.theme.rubikFont

@Composable
fun PlayingNow(
    title: String
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(visible = title.isNotEmpty()) {
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            fontFamily = rubikFont,
                            color = Color.White,
                            fontSize = 16.sp,
                        )
                    ) {
                        append("Tocando agora: ")
                    }
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Medium,
                            fontFamily = rubikFont,
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    ) {
                        append(title)
                    }
                },
                modifier = Modifier
                    .padding(16.dp),
            )
        }
    }
}