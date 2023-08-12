package br.com.onixradio.romantica.presentation.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import br.com.onixradio.romantica.R

val rubikFont = FontFamily(
    Font(R.font.rubik_bold, FontWeight.Bold),
    Font(R.font.rubik_light, FontWeight.Light),
    Font(R.font.rubik_medium, FontWeight.Medium)
)
// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = rubikFont,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = rubikFont,
        fontWeight = FontWeight.Bold,
        fontSize = 40.sp,
        color = Color.White
    ),
    h3 = TextStyle(
        fontFamily = rubikFont,
        fontWeight = FontWeight.Light,
        fontSize = 16.sp,
        textAlign = TextAlign.Center,
        color = Color.White
    )
)

val boldStyle = SpanStyle(
    fontWeight = FontWeight.Bold,
    fontFamily = rubikFont,
    color = Color.White,
    fontSize = 16.sp,
)

val mediumStyle = SpanStyle(
    fontWeight = FontWeight.Medium,
    fontFamily = rubikFont,
    color = Color.White,
    fontSize = 16.sp,
)

