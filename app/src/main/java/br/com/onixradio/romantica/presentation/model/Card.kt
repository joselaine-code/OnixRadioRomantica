package br.com.onixradio.romantica.presentation.model

import androidx.annotation.DrawableRes

data class Card(
    @DrawableRes val image: Int,
    val contentDescription: String
)
