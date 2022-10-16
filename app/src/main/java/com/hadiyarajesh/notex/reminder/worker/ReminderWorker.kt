package com.hadiyarajesh.notex.reminder.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.hadiyarajesh.notex.R
import com.hadiyarajesh.notex.database.dao.ReminderDao
import com.hadiyarajesh.notex.database.entity.Reminder
import com.hadiyarajesh.notex.database.model.RepetitionStrategy
import com.hadiyarajesh.notex.reminder.notification.NotificationDTO
import com.hadiyarajesh.notex.reminder.notification.NotificationHelper
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.time.LocalDateTime
import java.time.ZoneOffset

@HiltWorker
class ReminderWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    val reminderDao: ReminderDao,
    private val notificationHelper: NotificationHelper
) : CoroutineWorker(appContext, workerParams) {

    private val reminderWorkManager = ReminderWorkManager(reminderDao)

    override suspend fun doWork(): Result {
        val reminderId =
            inputData.getLong(applicationContext.getString(R.string.reminder_instance_key), -1)
        if (reminderId == -1L) {
            return Result.failure()
        }
        val reminder: Reminder = reminderDao.getById(reminderId)

        val localDate: LocalDateTime =
            LocalDateTime.ofInstant(reminder.reminderTime, ZoneOffset.UTC)

        notificationHelper.createNotification(
            applicationContext,
            NotificationDTO(
                title = reminder.content,
                subTitle = "${localDate.hour}:${localDate.minute}",
                reminderId = reminderId,
                workerTag = inputData.getString(applicationContext.getString(R.string.worker_tag))
                    ?: ""
            )
        )


        if (reminder.repeat != RepetitionStrategy.None) {
            reminderWorkManager.createWorkRequestAndEnqueue(
                reminderId = reminderId,
                context = applicationContext,
                isFirstTime = false,
                time = reminder.reminderTime
            )
        }
        return Result.success()
    }

}
