package com.jt17.notification_native

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat

class NotificationManager {
    companion object {
        private const val CHANNEL_ID: String = "test_notification_channel"
        fun testNotification(context: Context, textFromFlutter: String): Notification {
            val notificationManager: NotificationManager? =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    CHANNEL_ID,
                    "tes_notification",
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = "notification for text"
                }

                notificationManager?.createNotificationChannel(channel)
            }

            val intent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Intent(context, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
            } else {
                TODO("VERSION.SDK_INT < O")
            }

            val pendingIntent: PendingIntent = PendingIntent.getActivity(
                context,
                1000,
                intent,
                PendingIntent.FLAG_IMMUTABLE,
            )

            val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setColor(Color.parseColor("#FF5CCAFA"))
                .setContentTitle("Did you know the translation of this word?")
                .setContentText("text from flutter: $textFromFlutter")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setVibrate(longArrayOf(200L, 400L, 300L))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()

            val notificationId = System.currentTimeMillis().toInt()

            notificationManager?.notify(notificationId, notificationBuilder)

            return notificationBuilder
        }
    }
}