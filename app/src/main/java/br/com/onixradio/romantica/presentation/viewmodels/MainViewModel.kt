package br.com.onixradio.romantica.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.metadata.Metadata
import com.google.android.exoplayer2.metadata.icy.IcyInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val exoPlayer: ExoPlayer
) : ViewModel() {

    private val _nowPlaying = MutableStateFlow("")
    val nowPlaying: StateFlow<String> = _nowPlaying

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    fun preparePlayer() {
        exoPlayer.prepare()
        exoPlayer.play()
    }

    fun pausePlayer() {
        exoPlayer.pause()
    }

    fun getCurrentMusic() {
        val listener = object : Player.Listener {
            override fun onMetadata(metadata: Metadata) {
                for (i in 0 until metadata.length()) {
                    val md = metadata[i]
                    if (md is IcyInfo) {
                        _nowPlaying.value = md.title.orEmpty()
                        break
                    }
                }
                super.onMetadata(metadata)
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                _isPlaying.value = isPlaying
            }
        }
        exoPlayer.addListener(listener)
    }

    override fun onCleared() {
        super.onCleared()
        exoPlayer.release()
    }
}