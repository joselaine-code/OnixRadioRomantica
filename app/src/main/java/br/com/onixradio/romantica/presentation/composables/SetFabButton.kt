package br.com.onixradio.romantica.presentation.composables

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.com.onixradio.romantica.R

@Composable
fun SetFabButton(
        isAudioPlaying: Boolean,
        onClick: () -> Unit,
) {
    val buttonIcon = if (isAudioPlaying) {
        painterResource(id = R.drawable.ic_pause)
    } else {
        painterResource(id = R.drawable.ic_play)
    }

    val contentDescription = if (isAudioPlaying) {
        "Toque para pausar"
    } else {
        "Toque para dar play"
    }

    FloatingActionButton(
            shape = CircleShape,
            onClick = { onClick() },
            backgroundColor = Color.Red,
            contentColor = Color.White,
            modifier = Modifier.size(60.dp)
    ) {
        Icon(
                painter = buttonIcon,
                contentDescription = contentDescription,
                tint = Color.White
        )
    }
}
