package com.hadiyarajesh.notex.reminder.notification

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.hadiyarajesh.notex.MainActivity
import com.hadiyarajesh.notex.R
import kotlin.random.Random

object NotificationHelper {

    fun createNotification(
        context: Context,
        title: String,
        text: String,
        reminderId: Long,
        workerTag: String
    ) {
        val actionIntent = Intent(context, MainActivity::class.java)
        val actionPendingIntent = PendingIntent.getActivity(
            context, 0,
            actionIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )


        val postponeIntent = Intent(context, NotificationBroadCastReceiver::class.java).apply {
            action = context.resources.getString(R.string.postpone_action)
            putExtra(context.resources.getString(R.string.worker_tag), workerTag)
            putExtra(context.resources.getString(R.string.reminder_id), reminderId)
        }

        val postponePendingIntent = PendingIntent.getBroadcast(
            context, 0,
            postponeIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        val cancelIntent = Intent(context, NotificationBroadCastReceiver::class.java).apply {
            action = context.resources.getString(R.string.done_action)
            putExtra(context.resources.getString(R.string.worker_tag), workerTag)
        }

        val cancelPendingIntent = PendingIntent.getBroadcast(
            context, 0,
            cancelIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder =
            NotificationCompat.Builder(context, context.getString(R.string.notification_channel_id))
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setFullScreenIntent(actionPendingIntent, true)
                .addAction(R.drawable.ic_note_filled, "1 hour", postponePendingIntent)
                .addAction(R.drawable.ic_note_filled, "Done", cancelPendingIntent)


        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(Random(121).nextInt(1000), builder.build())
        }
    }
}