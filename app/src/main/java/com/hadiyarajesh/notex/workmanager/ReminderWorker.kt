package com.hadiyarajesh.notex.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.hadiyarajesh.notex.utility.Constants.CHANNEL_ID
import com.hadiyarajesh.notex.utility.NotificationHelper
import kotlin.math.log

class ReminderWorker(var context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {

    NotificationHelper(context).createNotification(
        inputData.getString("title").toString(),
        inputData.getString("message").toString(),
        inputData.getInt("notiid",2),

    )

        return Result.success()


    }




}