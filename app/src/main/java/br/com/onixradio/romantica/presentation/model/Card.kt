package br.com.onixradio.romantica.presentation.model

import androidx.annotation.DrawableRes
import javax.annotation.concurrent.Immutable

@Immutable
data class Card(
    @DrawableRes val image: Int,
    val contentDescription: String
)
