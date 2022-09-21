package com.hadiyarajesh.notex.utility

import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat

object Constants {
    object App {
        const val APP_NAME = "NoteX"
    }

    const val DEFAULT_REMINDER_NOTIFICATIONS_PRIORITY = NotificationCompat.PRIORITY_HIGH
    @RequiresApi(Build.VERSION_CODES.N)
    const val DEFAULT_REMINDER_CHANNEL_IMPORTANCE = NotificationManager.IMPORTANCE_HIGH

    const val KEY_REMINDER_ID = "KEY_REMINDER_ID"

}

const val TAG = Constants.App.APP_NAME
