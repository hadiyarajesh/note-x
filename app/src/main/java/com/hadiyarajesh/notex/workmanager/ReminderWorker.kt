package com.hadiyarajesh.notex.workmanager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.hadiyarajesh.notex.R
import com.hadiyarajesh.notex.database.entity.Reminder
import com.hadiyarajesh.notex.database.model.Notification
import com.hadiyarajesh.notex.database.model.RepetitionStrategy
import com.hadiyarajesh.notex.repository.reminders.RemindersRepository
import com.hadiyarajesh.notex.ui.testreminder.ReminderWorkerBuilder
import com.hadiyarajesh.notex.utility.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@HiltWorker
class ReminderWorker @AssistedInject constructor(
    @Assisted var context: Context,
    @Assisted params: WorkerParameters,
    private val repository: RemindersRepository
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val id = inputData.getLong(Constants.KEY_REMINDER_ID, 1)
        val reminder = repository.getReminderById(id)
        dowork(
            reminder,
            context
        )

        return Result.success()

    }

    fun dowork(reminder: Reminder, context: Context) {
        val notification = Notification(
            reminder.reminderId!!.toInt(),
            context.getString(R.string.notification_channel_id),
            context.getString(R.string.app_name),
            reminder.content,
            Constants.DEFAULT_REMINDER_NOTIFICATIONS_PRIORITY
        )
        NotificationHelper.createNotification(notification, context)
        val strategy = reminder.repeat


        if (strategy == RepetitionStrategy.None) {

            CoroutineScope(IO).launch {
                repository.markcompleted(reminder)
            }

            return
        }


      val durationunit=  strategy.getDurationUnitfromStrategy()


        val data = workDataOf(
            Constants.KEY_REMINDER_ID to reminder.reminderId
        )
        val delay = durationunit.getTimeDifference()

        val instant = Instant.now().plus(delay, ChronoUnit.MILLIS)

        val formattedInstant: String =
            DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a").withZone(ZoneId.systemDefault())
                .format(instant);
        val oneTimeWorkRequest = ReminderWorkerBuilder().createOneTimeWorkRequest(
            delay, data, "Reminder ReScheduled at: $formattedInstant"
        )

        WorkManager.getInstance(context).enqueueUniqueWork(
            reminder.reminderId.toString(), ExistingWorkPolicy.REPLACE,
            oneTimeWorkRequest
        )

        CoroutineScope(IO).launch {
            repository.reScheduleReminder(instant, reminder)
        }


    }

}