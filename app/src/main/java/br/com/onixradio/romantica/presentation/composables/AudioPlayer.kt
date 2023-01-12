package br.com.onixradio.romantica.presentation.composables

import android.util.Log
import androidx.compose.material.FloatingActionButton
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import br.com.onixradio.romantica.R
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.metadata.Metadata
import com.google.android.exoplayer2.metadata.icy.IcyInfo
import com.google.android.exoplayer2.ui.StyledPlayerView


@Composable
fun AudioPlayer(
    setTitle: (String?) -> Unit,
) {
    val playWhenReady = true
    val currentWindow = 0
    val playbackPosition = 0L
    var isPlaying by remember { mutableStateOf(false) }

    val mExoPlayer = ExoPlayer.Builder(LocalContext.current)
        .build()
        .also { exoPlayer ->
            val mediaItem = MediaItem.fromUri(stringResource(R.string.url_streaming))
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.playWhenReady = playWhenReady
            exoPlayer.seekTo(currentWindow, playbackPosition)
            addListener(isPlaying, setTitle, exoPlayer)
        }
    AndroidView(factory = { context ->
        StyledPlayerView(context).apply {
            player = mExoPlayer
        }
    })
}

@Composable
private fun addListener(
    isPlaying: Boolean,
    setTitle: (String?) -> Unit,
    exoPlayer: ExoPlayer
) {
    var isPlayingNow = isPlaying
    DisposableEffect(Unit) {
        val listener = object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying_: Boolean) {
                isPlayingNow = isPlaying_
            }

            override fun onMetadata(metadata: Metadata) {
                for (n in 0 until metadata.length()) {
                    when (val md = metadata[n]) {
                        is IcyInfo -> {
                            setTitle(md.title)
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
        onDispose {
            exoPlayer.removeListener(listener)
        }
    }
}
