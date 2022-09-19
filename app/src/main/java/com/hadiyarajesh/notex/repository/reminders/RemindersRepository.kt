package com.hadiyarajesh.notex.repository.reminders

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hadiyarajesh.notex.database.dao.ReminderDao
import com.hadiyarajesh.notex.database.entity.Reminder
import com.hadiyarajesh.notex.database.model.RepetitionStrategy
import com.hadiyarajesh.notex.reminder.worker.ReminderWorkManager
import com.hadiyarajesh.notex.reminder.worker.ReminderWorker
import kotlinx.coroutines.flow.Flow
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemindersRepository @Inject constructor(
    private val reminderDao: ReminderDao
) {
    suspend fun createReminder(
        title: String,
        reminderTime: Instant,
        repeat: RepetitionStrategy,
        context: Context? = null
    ) {
        val reminder = Reminder(
            content = title,
            reminderTime = reminderTime,
            repeat = repeat,
            cancelled = false,
            completed = false,
            completedOn = null,
            createdOn = Instant.now(),
            updatedOn = Instant.now(),
        )

        val reminderId = reminderDao.insertOrUpdate(
            reminder
        )

        val reminderWorkManager = ReminderWorkManager(reminderDao)
        reminderWorkManager.reminderDao = reminderDao
        if(context != null)
            reminderWorkManager.createWorkRequestAndEnqueue(context, reminderId = reminderId, time = reminderTime)
    }

    fun getAllReminders(): Flow<PagingData<Reminder>> = Pager(
        config = PagingConfig(pageSize = 20)
    ) {
        reminderDao.getAllByDesc()
    }.flow
}
