package br.com.onixradio.romantica.presentation.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import br.com.onixradio.romantica.R
import br.com.onixradio.romantica.presentation.ui.theme.Typography
import br.com.onixradio.romantica.presentation.ui.theme.WhatsappColor


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
        LogoSection()
        DescriptionSection(
                year = "2008",
                text = "A Ônix Rádio surgiu da paixão de José Carlos por músicas antigas no ano de 2008. Desde então, ela segue sendo uma das rádios mais ouvidas da região de São José do Rio Preto/SP e possui ouvintes pelo mundo todo.",
        )
        DescriptionSection(
                year = "2017",
                text = "Em 2017, atendendo aos pedidos dos ouvintes, foi inaugurada a Ônix Rádio Sertaneja.",
        )
        WhatsappIconButton(context = context, modifier = Modifier.padding(top = 20.dp))
    }
}

@Composable
private fun LogoSection() {
    Image(
            painter = painterResource(R.drawable.red_logo),
            contentDescription = "Ondas de Rádio",
            modifier = Modifier.size(200.dp)
    )
}

@Composable
private fun DescriptionSection(year: String, text: String) {
    Column(
            modifier = Modifier.padding(top = 20.dp),
    ) {
        Text(
                text = year,
                style = Typography.caption,
        )
        Text(
                text = text,
                style = Typography.h3,
                modifier = Modifier.padding(top = 8.dp),
        )
    }
}

@Composable
private fun WhatsappIconButton(context: Context, modifier: Modifier) {
    Button(
            onClick = { sendWhatsApp(context) },
            colors = ButtonDefaults.buttonColors(
                    backgroundColor = WhatsappColor,
                    contentColor = Color.White
            ),
            modifier = modifier,
            shape = RoundedCornerShape(16.dp)
    ) {
        Row {
            Text(text = "Entre em contato conosco no Whatsapp")
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