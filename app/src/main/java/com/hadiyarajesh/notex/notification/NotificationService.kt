package com.hadiyarajesh.notex.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.hadiyarajesh.notex.MainActivity
import com.hadiyarajesh.notex.R
import com.hadiyarajesh.notex.utility.Constants
import okhttp3.internal.notify
import java.util.*

class NotificationService(
    private val context:Context,
    private val noticifationId: Int
) {

    private  val notificationManager=context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    fun showNotification(counter:Int,priority:Int){
        val activityIntent= Intent(context, MainActivity::class.java)
        val activityPend=PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)PendingIntent.FLAG_IMMUTABLE else 0
        )
        val incement=PendingIntent.getBroadcast(
            context,
            1,
            Intent(context,NotificationReceiver::class.java),
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)PendingIntent.FLAG_IMMUTABLE else 0
        )
        val notification=NotificationCompat.Builder(context,noticifationId.toString())
            .setSmallIcon(R.drawable.ic_task_filled)
            .setContentTitle(Constants.App.APP_NAME)
            .setContentText("Action is clicked $counter times")
            .setContentIntent(activityPend)
            .setPriority(priority)
            .addAction(
                R.drawable.ic_note_outlined,
            "Take Action",
            incement).build()
        notificationManager.notify(
            1,notification
        )
    }
}