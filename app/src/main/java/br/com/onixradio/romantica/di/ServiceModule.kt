package br.com.onixradio.romantica.di

import android.app.NotificationManager
import android.content.Context
import br.com.onixradio.romantica.R
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MediaServiceModule {
    @Provides
    @Singleton
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

    @Provides
    @Singleton
    fun provideNotificationManager(@ApplicationContext context: Context): NotificationManager {
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

}
