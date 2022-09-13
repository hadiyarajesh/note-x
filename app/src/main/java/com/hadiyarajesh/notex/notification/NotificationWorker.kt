package com.hadiyarajesh.notex.notification

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.hadiyarajesh.notex.utility.Constants

class NotificationWorker(private val context: Context,
private val workerParameters: WorkerParameters):CoroutineWorker(context,workerParameters) {
    override suspend fun doWork(): Result {
        val id=inputData.getInt(Constants.Notification_Service_key,0)
        //val priority=inputData.getInt("Priority",2)
        triggerNotification(id,NotificationCompat.PRIORITY_HIGH)
        return Result.success()
    }

    private fun triggerNotification(notificationId:Int, priority:Int) {
        val service=NotificationService(context,notificationId)
        Counter.value=0
        service.showNotification(Counter.value,priority)
    }
}