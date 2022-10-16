package com.hadiyarajesh.notex.reminder.notification

import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.hadiyarajesh.notex.MainActivity
import com.hadiyarajesh.notex.R
import com.hadiyarajesh.notex.utility.Constants

class NotificationHelper {

    fun createNotification(
        context: Context,
        notificationDTO: NotificationDTO
    ) {
        val actionIntent = Intent(context, MainActivity::class.java)
        val actionPendingIntent = PendingIntent.getActivity(
            context, 0,
            actionIntent, PendingIntent.FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
        )

        val notificationId = notificationDTO.reminderId.toInt()

        val postponeIntent = Intent(context, NotificationBroadCastReceiver::class.java).apply {
            action = context.resources.getString(R.string.postpone_action)
            putExtra(context.resources.getString(R.string.worker_tag), notificationDTO.workerTag)
            putExtra(context.resources.getString(R.string.reminder_id), notificationDTO.reminderId)
            putExtra(Constants.NOTIFICATION_ID, notificationId)
        }

        val postponePendingIntent = PendingIntent.getBroadcast(
            context, 0,
            postponeIntent, PendingIntent.FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
        )

        val cancelIntent = Intent(context, NotificationBroadCastReceiver::class.java).apply {
            action = context.resources.getString(R.string.complete_action)
            putExtra(context.resources.getString(R.string.worker_tag), notificationDTO.workerTag)
            putExtra(Constants.NOTIFICATION_ID, notificationId)
        }

        val cancelPendingIntent = PendingIntent.getBroadcast(
            context, 0,
            cancelIntent, PendingIntent.FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
        )

        val builder =
            NotificationCompat.Builder(context, context.getString(R.string.notification_channel_id))
                .setSmallIcon(R.drawable.ic_note_filled)
                .setContentTitle(notificationDTO.title)
                .setContentText(notificationDTO.subTitle)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(actionPendingIntent)
                .addAction(R.drawable.ic_note_filled, context.getString(R.string.one_hour), postponePendingIntent)
                .addAction(R.drawable.ic_note_filled, context.getString(R.string.done), cancelPendingIntent)
                .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(notificationId, builder.build())
        }
    }
}
