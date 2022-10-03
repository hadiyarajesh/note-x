package com.hadiyarajesh.notex.reminder.worker

import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.hadiyarajesh.notex.R
import com.hadiyarajesh.notex.database.dao.ReminderDao
import com.hadiyarajesh.notex.database.entity.Reminder
import com.hadiyarajesh.notex.database.model.RepetitionStrategy
import com.hadiyarajesh.notex.reminder.MainNotificationWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ReminderWorkManager @Inject constructor(val reminderDao: ReminderDao) :
    MainNotificationWorker {


    override fun createWorkRequestAndEnqueue(
        context: Context,
        reminder: Reminder?,
        requestType: MainNotificationWorker.Companion.RequestType,
        isFirstTime: Boolean
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            reminder?.let { reminder ->
                val data: Data.Builder = Data.Builder()
                data.putLong(
                    context.resources.getString(R.string.reminder_instance_key),
                    reminder.reminderId!!
                )

                val workerTag =
                    "${context.resources.getString(R.string.reminder_worker_tag)}${reminder.reminderId}"

                data.putString(context.resources.getString(R.string.worker_tag), workerTag)

                val initialDelay =
                    if (isFirstTime) reminder.reminderTime.toEpochMilli() - Instant.now()
                        .toEpochMilli()
                    else getDurationInMilli(
                        reminderStrategy = reminder.repeat,
                        reminderTime = reminder.reminderTime
                    )

                val dailyWorkRequest: WorkRequest = when (requestType) {
                    MainNotificationWorker.Companion.RequestType.OneTimeRequest -> OneTimeWorkRequestBuilder<ReminderWorker>()
                        .setInitialDelay(
                            initialDelay,
                            TimeUnit.MILLISECONDS
                        )
                        .setInputData(data.build())
                        .addTag(workerTag)
                        .build()
                    MainNotificationWorker.Companion.RequestType.PeriodicRequest -> TODO()
                }
                WorkManager.getInstance(context)
                    .enqueue(dailyWorkRequest)
            }
        }

    }

    override fun postponeRequestAndEnqueue(context: Context, reminderId: Long, time: Instant) {
        CoroutineScope(Dispatchers.IO).launch {
            val reminder = reminderDao.getById(reminderId)
            createWorkRequestAndEnqueue(context, reminder.copy(reminderTime = time))
        }
    }

    override fun cancelWorkRequest(context: Context, tag: String): Boolean {
        val cancelled = WorkManager.getInstance(context).cancelAllWorkByTag(tag)
        //doubt needed  review
        return cancelled.result.isDone
    }


    private fun getDurationInMilli(
        reminderStrategy: RepetitionStrategy,
        reminderTime: Instant
    ): Long {
        val duration: Long
        when (reminderStrategy) {
            RepetitionStrategy.Daily -> {
                duration = Instant.now().plus(1, ChronoUnit.DAYS).toEpochMilli()
            }
            RepetitionStrategy.Monthly -> {
                duration = Instant.now().plus(1, ChronoUnit.MONTHS).toEpochMilli()
            }
            RepetitionStrategy.Yearly -> {
                duration = Instant.now().plus(1, ChronoUnit.YEARS).toEpochMilli()
            }
            RepetitionStrategy.Weekly -> {
                duration = Instant.now().plus(1, ChronoUnit.WEEKS).toEpochMilli()
            }
            else -> {
                duration = reminderTime.toEpochMilli()
            }
        }
        return duration - Instant.now().toEpochMilli()
    }


}