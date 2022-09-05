package com.hadiyarajesh.notex.widgets.reminder

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.updateAll
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.hadiyarajesh.notex.database.entity.Reminder
import com.hadiyarajesh.notex.database.model.RepetitionStrategy
import com.hadiyarajesh.notex.repository.reminders.RemindersRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import java.time.Duration
import java.time.Instant
import java.util.*
import java.util.concurrent.TimeUnit

@HiltWorker
class ReminderWidgetWorker  @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val repository: RemindersRepository
) : CoroutineWorker(context, workerParameters) {

    companion object {
        private val uniqueWorkName = ReminderWidgetWorker::class.java.simpleName

        fun enqueue(context: Context) {
            val manager = WorkManager.getInstance(context)

            val requestBuilder = PeriodicWorkRequestBuilder<ReminderWidgetWorker>(
                Duration.ofMinutes(30)
            ).setInitialDelay(
                (getNextDayStart() - System.currentTimeMillis()),
                TimeUnit.MILLISECONDS
            )

            manager.enqueueUniquePeriodicWork(
                uniqueWorkName,
                ExistingPeriodicWorkPolicy.KEEP,
                requestBuilder.build()
            )
        }

        fun enqueueOnce(context: Context) {
            val manager = WorkManager.getInstance(context)

            val requestBuilder = OneTimeWorkRequestBuilder<ReminderWidgetWorker>()

            manager.enqueueUniqueWork(
                "$uniqueWorkName-u",
                ExistingWorkPolicy.KEEP,
                requestBuilder.build()
            )
        }

        private fun getNextDayStart(): Long {
            val calendar = Calendar.getInstance()
            calendar[Calendar.HOUR_OF_DAY] = 0
            calendar[Calendar.MINUTE] = 1
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0
            calendar.add(Calendar.DATE, 1)
            return calendar.timeInMillis
        }

        fun cancel(context: Context) {
            WorkManager.getInstance(context).cancelUniqueWork(uniqueWorkName)
        }
    }

    private val widgetManager: GlanceAppWidgetManager = GlanceAppWidgetManager(context)

    override suspend fun doWork(): Result {
        return try {
            setupDataForReminderWidget()
            Result.success()
        } catch (e: Exception) {
            if (runAttemptCount < 5)
                Result.retry()
            else
                Result.failure()
        }
    }

    private suspend fun setupDataForReminderWidget() {
        val glanceIds = widgetManager.getGlanceIds(ReminderWidget::class.java)
        //todo: retrieving data to the state
        delay(5000)
//        setWidgetState(
//            glanceIds, ReminderData.Available(
//                listOf(
//                    Reminder(
//                        content = "Reminder 1",
//                        reminderTime = Instant.now(),
//                        repeat = RepetitionStrategy.None,
//                        cancelled = false,
//                        completed = true,
//                        completedOn = Instant.now(),
//                        createdOn = Instant.now(),
//                        updatedOn = Instant.now()
//                    )
//                )
//            )
//        )
//        delay(5000)
        setWidgetState(
            glanceIds, ReminderData.Available(
                listOf(
                    Reminder(
                        content = "Reminder 1",
                        reminderTime = Instant.now(),
                        repeat = RepetitionStrategy.None,
                        cancelled = false,
                        completed = true,
                        completedOn = Instant.now(),
                        createdOn = Instant.now(),
                        updatedOn = Instant.now()
                    ),
                    Reminder(
                        content = "Reminder 2",
                        reminderTime = Instant.now(),
                        repeat = RepetitionStrategy.None,
                        cancelled = false,
                        completed = true,
                        completedOn = Instant.now(),
                        createdOn = Instant.now(),
                        updatedOn = Instant.now()
                    ),
                    Reminder(
                        content = "Reminder 3",
                        reminderTime = Instant.now(),
                        repeat = RepetitionStrategy.None,
                        cancelled = false,
                        completed = true,
                        completedOn = Instant.now(),
                        createdOn = Instant.now(),
                        updatedOn = Instant.now()
                    ),
                    Reminder(
                        content = "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
                        reminderTime = Instant.now(),
                        repeat = RepetitionStrategy.None,
                        cancelled = false,
                        completed = true,
                        completedOn = Instant.now(),
                        createdOn = Instant.now(),
                        updatedOn = Instant.now()
                    ),
                    Reminder(
                        content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.",
                        reminderTime = Instant.now(),
                        repeat = RepetitionStrategy.None,
                        cancelled = false,
                        completed = true,
                        completedOn = Instant.now(),
                        createdOn = Instant.now(),
                        updatedOn = Instant.now()
                    ),
                )
            )
        )
        // delay(5000)
        //setWidgetState(glanceIds, ReminderData.Unavailable("Data unavailable.."))


    }

    private suspend fun setWidgetState(glanceIds: List<GlanceId>, newState: ReminderData) {

        glanceIds.forEach { glanceId ->
            updateAppWidgetState(
                context = context,
                definition = ReminderDataStateDefinition,
                glanceId = glanceId,
                updateState = { newState }
            )
        }

        ReminderWidget().updateAll(context)

    }
}