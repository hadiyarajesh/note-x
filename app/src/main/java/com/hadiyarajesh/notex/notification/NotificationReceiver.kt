package com.hadiyarajesh.notex.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.hadiyarajesh.notex.R

class NotificationReceiver:BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val service= NotificationService(context,context.getString(R.string.notification_channel_id).toInt())
        service.showNotification(++Counter.value,2)
    }
}