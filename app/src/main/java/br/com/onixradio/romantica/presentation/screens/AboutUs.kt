package br.com.onixradio.romantica

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import br.com.onixradio.romantica.presentation.ui.theme.Typography


@Composable
fun AboutUs() {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(R.drawable.red_logo),
            contentDescription = "Ondas de Rádio",
            modifier = Modifier.size(200.dp)
        )
        Text(text = "2008", style = Typography.caption, modifier = Modifier.padding(top = 20.dp))
        Text(
            text = "A Ônix Rádio surgiu da paixão de José Carlos por músicas antigas no ano de 2008." +
                    " Desde então, ela segue sendo uma das rádios mais ouvidas da região de São José do Rio Preto/SP e possui ouvintes pelo mundo todo.",
            style = Typography.h3,
            modifier = Modifier.padding(top = 20.dp)
        )
        Text(text = "2017", style = Typography.caption, modifier = Modifier.padding(top = 20.dp))
        Text(
            text = "Em 2017, atendendo aos pedidos dos ouvintes, foi inaugurada a Ônix Rádio Sertaneja.",
            style = Typography.h3,
            modifier = Modifier.padding(top = 20.dp)
        )
        IconButton(onClick = { sendWhatsApp(context = context) }) {
            Image(
                painter = painterResource(id = R.drawable.icon_whats),
                contentDescription = "Enviar whatsapp",
                modifier = Modifier
                    .padding(top = 20.dp)
                    .clip(CircleShape)
                    .size(50.dp)
            )

        }
    }
}

private fun sendWhatsApp(context: Context) {
    val url = context.getString(R.string.url_whatsapp)
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(url)
    startActivity(context, intent, null)
}

@Preview
@Composable
fun AboutUsPreview() {
    AboutUs()
}