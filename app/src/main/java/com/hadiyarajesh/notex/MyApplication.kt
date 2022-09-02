package com.hadiyarajesh.notex

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.hadiyarajesh.notex.utility.Constants.CHANNEL_DESCRIPTION
import com.hadiyarajesh.notex.utility.Constants.CHANNEL_ID
import com.hadiyarajesh.notex.utility.Constants.CHANNEL_NAME
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication: Application(){


    override fun onCreate() {
        super.onCreate()
        createNotificationChannels();
    }
    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = CHANNEL_DESCRIPTION

            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(channel)
        }
    }

}
