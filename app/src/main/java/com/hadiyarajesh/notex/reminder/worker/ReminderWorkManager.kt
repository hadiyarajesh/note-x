package com.hadiyarajesh.notex.reminder.worker

import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.hadiyarajesh.notex.R
import com.hadiyarajesh.notex.database.dao.ReminderDao
import com.hadiyarajesh.notex.database.model.RepetitionStrategy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ReminderWorkManager @Inject constructor(var reminderDao: ReminderDao) {


    fun createWorkRequestAndEnqueue(
        context: Context,
        reminderId: Long,
        time: Instant,
        isFirstTime: Boolean = true
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val data: Data.Builder = Data.Builder()
            data.putLong(
                context.resources.getString(R.string.reminder_instance_key),
                reminderId
            )

            val workerTag =
                "${context.resources.getString(R.string.reminder_worker_tag)}${reminderId}"

            data.putString(context.resources.getString(R.string.worker_tag), workerTag)


            val reminder = reminderDao.getById(reminderId)

            val initialDelay = if (isFirstTime) time.toEpochMilli() - Instant.now().toEpochMilli()
            else getDurationInMilli(reminderStrategy = reminder.repeat, reminderTime = time)

            val dailyWorkRequest: WorkRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
                .setInitialDelay(
                    initialDelay,
                    TimeUnit.MILLISECONDS
                )
                .setInputData(data.build())
                .addTag(workerTag)
                .build()
            WorkManager.getInstance(context)
                .enqueue(dailyWorkRequest)
        }

    }


    private fun getDurationInMilli(
        reminderStrategy: RepetitionStrategy,
        reminderTime: Instant
    ): Long {
        var duration: Long = 0
        if (reminderStrategy == RepetitionStrategy.Daily) {
            duration = Instant.now().plus(1, ChronoUnit.DAYS).toEpochMilli()
        } else if (reminderStrategy == RepetitionStrategy.Monthly) {
            duration = Instant.now().plus(1, ChronoUnit.MONTHS).toEpochMilli()
        } else if (reminderStrategy == RepetitionStrategy.Yearly) {
            duration = Instant.now().plus(1, ChronoUnit.YEARS).toEpochMilli()
        } else if (reminderStrategy == RepetitionStrategy.Weekly) {
            duration = Instant.now().plus(1, ChronoUnit.WEEKS).toEpochMilli()
        } else {
            duration = reminderTime.toEpochMilli()
        }
        return duration - Instant.now().toEpochMilli()
    }

    fun cancelWorkRequest(context: Context, tag: String) =
        WorkManager.getInstance(context).cancelAllWorkByTag(tag)
}