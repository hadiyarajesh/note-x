package com.hadiyarajesh.notex.utility

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.hadiyarajesh.notex.MainActivity
import com.hadiyarajesh.notex.R
import com.hadiyarajesh.notex.utility.Constants.CHANNEL_ID


class NotificationHelper(var context: Context) {


        fun createNotification(title: String, message: String,notiid:Int){

            val intent = Intent(context, MainActivity:: class.java).apply{
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

            val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_note_outlined)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(message)
                )
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build()
            NotificationManagerCompat.from(context).notify(notiid, notification)

        }

    }

