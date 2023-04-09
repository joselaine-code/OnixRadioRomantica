package br.com.onixradio.romantica.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.onixradio.romantica.R
import br.com.onixradio.romantica.presentation.ui.theme.rubikFont

@Composable
fun WelcomeToApp() {
    Column(
        modifier = Modifier.padding(30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row {
            Text(
                text = "Ônix Rádio Romântica",
                textAlign = TextAlign.Center,
                color = Color.White,
                fontFamily = rubikFont,
                fontSize = 25.sp,
                modifier = Modifier.weight(1f)
                    .align(Alignment.CenterVertically)
            )
            Image(
                painter = painterResource(R.drawable.red_waves),
                contentDescription = "Ondas de Rádio",
                modifier = Modifier.size(50.dp)
            )
        }

    }
}