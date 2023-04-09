package br.com.onixradio.romantica.di

import android.content.Context
import br.com.onixradio.romantica.R
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MediaServiceModule {
    @Provides
    fun provideExoPlayer(@ApplicationContext context: Context): ExoPlayer =
        ExoPlayer.Builder(context).build().apply {
            val playWhenReady = true
            val currentWindow = 0
            val playbackPosition = 0L
            val mediaItem = MediaItem.fromUri(context.getString(R.string.url_streaming))
            setHandleAudioBecomingNoisy(true)
            setMediaItem(mediaItem)
            setPlayWhenReady(playWhenReady)
            seekTo(currentWindow, playbackPosition)
        }

}