package com.hadiyarajesh.notex.repository.reminders

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hadiyarajesh.notex.database.dao.ReminderDao
import com.hadiyarajesh.notex.database.entity.Reminder
import com.hadiyarajesh.notex.database.model.RepetitionStrategy
import com.hadiyarajesh.notex.reminder.ReminderService
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow

@Singleton
class RemindersRepository @Inject constructor(
    private val reminderDao: ReminderDao,
    private val notificationService: ReminderService
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
        context?.let {
            notificationService.createWorkRequestAndEnqueue(
                it,
                reminder = reminder.copy(reminderId = reminderId)
            )
        }
    }

    fun getAllReminders(): Flow<PagingData<Reminder>> = Pager(
        config = PagingConfig(pageSize = 20)
    ) {
        reminderDao.getAllByDesc()
    }.flow
}
