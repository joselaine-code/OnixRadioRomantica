package br.com.onixradio.romantica.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.metadata.Metadata
import com.google.android.exoplayer2.metadata.icy.IcyInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val exoPlayer: ExoPlayer
) : ViewModel() {

    var string: String? = ""

    fun test() {
        exoPlayer.prepare()
        exoPlayer.play()
    }

    fun isNowPlaying(): Boolean {
        return exoPlayer.isPlaying
    }

    fun musicNowPlaying() {
        val listener = object : Player.Listener {
            override fun onMetadata(metadata: Metadata) {
                for (n in 0 until metadata.length()) {
                    when (val md = metadata[n]) {
                        is IcyInfo -> {
                            string = md.title
                        }
                        else -> {
                            Log.d("tag", "Some other sort of metadata: $md")
                        }
                    }
                    super.onMetadata(metadata)
                }
            }
        }
        exoPlayer.addListener(listener)
    }
}