package com.hadiyarajesh.notex.repository.reminders

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hadiyarajesh.notex.database.dao.ReminderDao
import com.hadiyarajesh.notex.database.entity.Reminder
import com.hadiyarajesh.notex.database.model.RepetitionStrategy
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
        repeat: RepetitionStrategy
    ): Long {
        return reminderDao.insertOrUpdate(
            Reminder(
                content = title,
                reminderTime = reminderTime,
                repeat = repeat,
                cancelled = false,
                completed = false,
                completedOn = null,
                createdOn = Instant.now(),
                updatedOn = Instant.now(),
            )
        )
    }

    suspend fun updatereminder(
        reminder: Reminder
    ): Long {
        return reminderDao.insertOrUpdate(
            Reminder(
                content = reminder.content,
                reminderTime = reminder.reminderTime,
                repeat = reminder.repeat,
                cancelled = reminder.cancelled,
                completed = reminder.completed,
                completedOn = reminder.completedOn,
                createdOn = reminder.createdOn,
                updatedOn = Instant.now(),
            )
        )
    }

    suspend fun markcompleted(
        reminder: Reminder
    ): Long {
        return reminderDao.insertOrUpdate(
            Reminder(                reminderId=reminder.reminderId,

                content = reminder.content,
                reminderTime = reminder.reminderTime,
                repeat = reminder.repeat,
                cancelled = reminder.cancelled,
                completed = true,
                completedOn = Instant.now(),
                createdOn = reminder.createdOn,
                updatedOn = Instant.now(),
            )
        )
    }

    suspend fun reScheduleReminder(
        remindertime: Instant,
        reminder: Reminder
    ): Long {
        return reminderDao.insertOrUpdate(
            Reminder(
                reminderId=reminder.reminderId,
                content = reminder.content,
                reminderTime = remindertime,
                repeat = reminder.repeat,
                cancelled = false,
                completed = false,
                completedOn = null,
                createdOn = reminder.createdOn,
                updatedOn = Instant.now(),
            )
        )

    }

    fun getReminderById(id: Long) = reminderDao.getById(id)


    fun getAllReminders(): Flow<PagingData<Reminder>> = Pager(
        config = PagingConfig(pageSize = 20)
    ) {
        reminderDao.getAllByDesc()
    }.flow
}
