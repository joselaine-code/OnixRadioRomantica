package br.com.onixradio.romantica.data.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import br.com.onixradio.romantica.R
import com.google.android.exoplayer2.ExoPlayer
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ServiceScoped
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
@ServiceScoped
class MediaService() : Service() {

    @Inject
    lateinit var exoPlayerProvider: Provider<ExoPlayer>

    private lateinit var playPauseIntent: PendingIntent
    private lateinit var stopIntent: PendingIntent

    private lateinit var exoPlayer: ExoPlayer

    private var isForeground = false

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

        playPauseIntent = createPendingIntent(ACTION_PLAY)
        stopIntent = createPendingIntent(ACTION_PAUSE)

        val notificationLayout = RemoteViews(packageName, R.layout.notification_player)
        notificationLayout.setOnClickPendingIntent(R.id.notification_play_pause, playPauseIntent)
        notificationLayout.setOnClickPendingIntent(R.id.notification_stop, stopIntent)

        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle(getString(R.string.app_name))
            .setContentText(getString(R.string.app_name))
            .setSmallIcon(R.drawable.red_logo)
            .setContent(notificationLayout)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setAutoCancel(false)
            .setOngoing(true)

        val notification = notificationBuilder.build()

        startForeground(NOTIFICATION_ID, notification)
        isForeground = true
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        handleIntent(intent)
        return START_NOT_STICKY
    }

    private fun handleIntent(intent: Intent?) {
        when (intent?.action) {
            ACTION_PLAY -> {
                exoPlayer.playWhenReady = true
            }
            ACTION_PAUSE -> {
                exoPlayer.playWhenReady = false
            }
            ACTION_STOP -> {
                exoPlayer.stop()
                stopForeground(true)
                stopSelf()
                isForeground = false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isForeground) {
            stopForeground(true)
        }
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
