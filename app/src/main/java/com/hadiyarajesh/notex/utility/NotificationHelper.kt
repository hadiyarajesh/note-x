package com.hadiyarajesh.notex.utility

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import com.hadiyarajesh.notex.MainActivity
import com.hadiyarajesh.notex.R
import com.hadiyarajesh.notex.database.model.Notification
import com.hadiyarajesh.notex.database.model.NotificationsChannel


object NotificationHelper {


    fun createNotification(noti: Notification, context: Context) {

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val notification = NotificationCompat.Builder(context, noti.channelid)
            .setContentTitle(noti.title)
            .setSmallIcon(noti.icon)
            .setContentText(noti.content)
            .setContentIntent(pendingIntent)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(noti.content)
            )
            .setPriority(noti.priority)
            .build()
        NotificationManagerCompat.from(context).notify(noti.notificationid, notification)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationchannel(channel: NotificationsChannel, context: Context) {

            val notificationsChannel = NotificationChannel(
                channel.channelid,
                channel.channelname,
               channel.channelimportance
            )
            notificationsChannel.description = channel.channeldescription

            val manager = getSystemService(context,
                NotificationManager::class.java
            )
            manager?.createNotificationChannel(notificationsChannel)

    }

}

