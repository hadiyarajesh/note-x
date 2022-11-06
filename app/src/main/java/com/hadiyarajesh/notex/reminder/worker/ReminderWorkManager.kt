package com.hadiyarajesh.notex.reminder.worker

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.hadiyarajesh.notex.R
import com.hadiyarajesh.notex.database.dao.ReminderDao
import com.hadiyarajesh.notex.database.entity.Reminder
import com.hadiyarajesh.notex.database.model.RepetitionStrategy
import com.hadiyarajesh.notex.reminder.ReminderService
import com.hadiyarajesh.notex.utility.TAG
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReminderWorkManager @Inject constructor(var reminderDao: ReminderDao) : ReminderService {

    override fun createWorkRequestAndEnqueue(
        context: Context,
        reminder: Reminder?,
        isFirstTime: Boolean
    ) {
        reminder?.let { reminderObj ->
            CoroutineScope(Dispatchers.IO).launch {
                val data: Data.Builder = Data.Builder()
                data.putLong(
                    context.resources.getString(R.string.reminder_instance_key),
                    reminderObj.reminderId!!
                )

                val workerTag =
                    "${context.resources.getString(R.string.reminder_worker_tag)}${reminderObj.reminderId}"

                data.putString(context.resources.getString(R.string.worker_tag), workerTag)

                val initialDelay = if (isFirstTime) {
                    reminderObj.reminderTime.toEpochMilli() - Instant.now().toEpochMilli()
                } else {
                    getDurationInMilli(
                        reminderStrategy = reminder.repeat,
                        reminderTime = reminderObj.reminderTime
                    )
                }

                val dailyWorkRequest: WorkRequest =
                    when (reminderObj.repeat) {
                        RepetitionStrategy.None -> OneTimeWorkRequestBuilder<ReminderWorker>()
                            .setInitialDelay(
                                initialDelay,
                                TimeUnit.MILLISECONDS
                            )
                            .setInputData(data.build())
                            .addTag(workerTag)
                            .build()
                        RepetitionStrategy.Daily, RepetitionStrategy.Weekly, RepetitionStrategy.Monthly, RepetitionStrategy.Yearly -> { // same implementation for now need periodic changes
                            Log.i(
                                TAG,
                                "Not Implemented Performing Same Action as of Now for Repetition ${reminderObj.repeat.name}"
                            )
                            OneTimeWorkRequestBuilder<ReminderWorker>()
                                .setInitialDelay(
                                    initialDelay,
                                    TimeUnit.MILLISECONDS
                                )
                                .setInputData(data.build())
                                .addTag(workerTag)
                                .build()
                        }
                    }
                WorkManager.getInstance(context)
                    .enqueue(dailyWorkRequest)
            }
        }
    }

    override fun postponeRequestAndEnqueue(context: Context, reminderId: Long, time: Instant) {
        CoroutineScope(Dispatchers.IO).launch {
            val reminder = reminderDao.getById(reminderId)
            createWorkRequestAndEnqueue(
                context,
                reminder.copy(reminderTime = time),
                isFirstTime = false
            )
        }
    }

    private fun getDurationInMilli(
        reminderStrategy: RepetitionStrategy,
        reminderTime: Instant
    ): Long {
        val duration = when (reminderStrategy) {
            RepetitionStrategy.Daily -> Instant.now().plus(1, ChronoUnit.DAYS).toEpochMilli()
            RepetitionStrategy.Monthly -> Instant.now().plus(1, ChronoUnit.MONTHS).toEpochMilli()
            RepetitionStrategy.Yearly -> Instant.now().plus(1, ChronoUnit.YEARS).toEpochMilli()
            RepetitionStrategy.Weekly -> Instant.now().plus(1, ChronoUnit.WEEKS).toEpochMilli()
            else -> reminderTime.toEpochMilli()
        }
        return duration - Instant.now().toEpochMilli()
    }

    override fun cancelWorkRequest(context: Context, tag: String) {
        WorkManager.getInstance(context).cancelAllWorkByTag(tag)
    }
}
