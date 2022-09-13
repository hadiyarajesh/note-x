package com.hadiyarajesh.notex.notification

import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkRequest
import com.hadiyarajesh.notex.R
import com.hadiyarajesh.notex.utility.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LocalNotificationBuilder @Inject constructor(@ApplicationContext val context: Context) {

    fun createOneTimeScheduleNotification(scheduleTime: LocalDateTime) :WorkRequest {
        val data= Data.Builder().putInt(Constants.Notification_Service_key,context.getString(R.string.notification_channel_id).toInt()).build()
        val delay=getTimeDifference(scheduleTime)
       return OneTimeWorkRequest.Builder(NotificationWorker::class.java)
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .addTag("notification")
            .setInputData(data)
            .build()
    }

    private fun getTimeDifference(time: LocalDateTime): Long {
        val now= LocalDateTime.now()
        val duration= Duration.between(now,time).toMillis()
        return if(duration>0)duration else 0
    }
}