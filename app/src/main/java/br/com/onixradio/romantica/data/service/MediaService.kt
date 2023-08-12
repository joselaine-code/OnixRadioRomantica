package br.com.onixradio.romantica.data.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import br.com.onixradio.romantica.R
import com.google.android.exoplayer2.ExoPlayer
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ServiceScoped
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
@ServiceScoped
class MediaService : Service() {

    @Inject
    lateinit var exoPlayerProvider: Provider<ExoPlayer>

    private lateinit var playIntent: PendingIntent
    private lateinit var pauseIntent: PendingIntent
    private lateinit var stopIntent: PendingIntent

    private lateinit var exoPlayer: ExoPlayer

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        exoPlayer = exoPlayerProvider.get()
        startForegroundService()
    }

    private fun createPendingIntent(action: String): PendingIntent {
        val intent = Intent(applicationContext, MediaService::class.java)
        intent.action = action
        return PendingIntent.getService(this, 0, intent,  PendingIntent.FLAG_IMMUTABLE)
    }

    private fun startForegroundService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(notificationChannel)
        }

        playIntent = createPendingIntent(ACTION_PLAY)
        pauseIntent = createPendingIntent(ACTION_PAUSE)
        stopIntent = createPendingIntent(ACTION_STOP)

        val mediaSession = MediaSessionCompat(this, "AudioPlayerSession")

        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.red_logo)
            .setContentTitle(getString(R.string.app_name))
            .setContentText("A mais romântica")
            .addAction(R.drawable.ic_play, "Play", playIntent)
            .addAction(R.drawable.ic_pause, "Pause", pauseIntent)
            .addAction(R.drawable.ic_stop, "Stop", stopIntent)
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setShowActionsInCompactView(0,1,2)
                    .setMediaSession(mediaSession.sessionToken)
            )
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)


        val notification = notificationBuilder.build()

        startForeground(NOTIFICATION_ID, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        handleIntent(intent)
        return START_NOT_STICKY
    }

    private fun handleIntent(intent: Intent?) {
        when (intent?.action) {
            ACTION_PLAY -> {
                exoPlayer.prepare()
                exoPlayer.play()
            }

            ACTION_PAUSE -> {
                exoPlayer.pause()
            }

            ACTION_STOP -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    exoPlayer.stop()
                    stopForeground(STOP_FOREGROUND_REMOVE)
                } else {
                    exoPlayer.stop()
                    stopForeground(true)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(true)
        exoPlayer.release()
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