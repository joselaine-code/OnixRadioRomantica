package br.com.onixradio.romantica.presentation.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import br.com.onixradio.romantica.data.service.MediaService
import br.com.onixradio.romantica.presentation.composables.NavigationController
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        setContent {
            NavigationController()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val stopServiceIntent = Intent(applicationContext, MediaService::class.java)
        applicationContext.stopService(stopServiceIntent)
    }
}