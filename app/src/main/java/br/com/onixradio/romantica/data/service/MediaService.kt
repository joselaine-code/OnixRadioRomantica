package br.com.onixradio.romantica.data.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import androidx.media.app.NotificationCompat.MediaStyle
import br.com.onixradio.romantica.R
import com.google.android.exoplayer2.ExoPlayer
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class MediaService : Service() {

    @Inject
    lateinit var exoPlayerProvider: Provider<ExoPlayer>

    private lateinit var exoPlayer: ExoPlayer

    private val mediaSession: MediaSessionCompat by lazy {
        MediaSessionCompat(this, "AudioPlayerSession")
    }

    override fun onCreate() {
        super.onCreate()
        exoPlayer = exoPlayerProvider.get()
        startForegroundService()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let { handleIntent(it) }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun handleIntent(intent: Intent) {
        when (intent.action) {
            ACTION_PLAY -> {
                exoPlayer.prepare()
                exoPlayer.play()
            }

            ACTION_PAUSE -> {
                exoPlayer.pause()
            }

            ACTION_STOP -> {
                stopPlayback()
            }
        }
    }

    private fun stopPlayback() {
        exoPlayer.stop()
        stopForeground(true)
        mediaSession.isActive = false
        stopSelf()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        stopPlayback()
        super.onDestroy()
    }

    private fun startForegroundService() {
        createNotificationChannel()

        val playIntent = createPendingIntent(ACTION_PLAY)
        val pauseIntent = createPendingIntent(ACTION_PAUSE)
        val stopIntent = createPendingIntent(ACTION_STOP)

        val notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.red_logo)
            .setContentTitle(getString(R.string.app_name))
            .setContentText("A mais romântica")
            .addAction(R.drawable.ic_play, "Play", playIntent)
            .addAction(R.drawable.ic_pause, "Pause", pauseIntent)
            .addAction(R.drawable.ic_stop, "Stop", stopIntent)
            .setStyle(
                MediaStyle()
                    .setShowActionsInCompactView(0, 1, 2)
                    .setMediaSession(mediaSession.sessionToken)
            )
            .setDeleteIntent(stopIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .build()

        startForeground(NOTIFICATION_ID, notification)
        mediaSession.isActive = true
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(notificationChannel)
        }
    }

    private fun createPendingIntent(action: String): PendingIntent {
        val intent = Intent(applicationContext, MediaService::class.java)
        intent.action = action
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    }

    companion object {
        private const val NOTIFICATION_ID = 1
        private const val NOTIFICATION_CHANNEL_ID = "MediaServiceChannel"
        private const val NOTIFICATION_CHANNEL_NAME = "Media Service Channel"
        const val ACTION_PLAY = "br.com.onixradio.romantica.PLAY"
        const val ACTION_PAUSE = "br.com.onixradio.romantica.PAUSE"
        const val ACTION_STOP = "br.com.onixradio.romantica.STOP"
    }
}