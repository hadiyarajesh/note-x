package com.hadiyarajesh.notex.reminder.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.hadiyarajesh.notex.R
import com.hadiyarajesh.notex.database.dao.ReminderDao
import com.hadiyarajesh.notex.database.entity.Reminder
import com.hadiyarajesh.notex.database.model.RepetitionStrategy
import com.hadiyarajesh.notex.reminder.notification.NotificationHelper
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import javax.inject.Inject

@HiltWorker
class ReminderWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
) : CoroutineWorker(appContext, workerParams) {

    @Inject
    lateinit var reminderDao: ReminderDao

    private val reminderWorkManager = ReminderWorkManager()

    override suspend fun doWork(): Result {
        val reminderId =
            inputData.getLong(applicationContext.getString(R.string.reminder_instance_key), -1)
        if (reminderId == -1L)
            return Result.failure()
        val reminder: Reminder = reminderDao.getById(reminderId)

        NotificationHelper.createNotification(
            applicationContext,
            text = reminder.reminderTime.toString(),
            title = reminder.content,
            reminderId = reminderId,
            workerTag = inputData.getString(applicationContext.getString(R.string.worker_tag)) ?: ""
        )


        if (reminder.repeat != RepetitionStrategy.None) {
            reminderWorkManager.createWorkRequestAndEnqueue(
                reminderId= reminderId,
                context = applicationContext,
                isFirstTime = false,
                time = reminder.reminderTime
            )
        }
        return Result.success()
    }

}