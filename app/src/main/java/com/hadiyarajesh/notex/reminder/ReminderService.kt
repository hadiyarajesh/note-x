package com.hadiyarajesh.notex.reminder

import android.content.Context
import com.hadiyarajesh.notex.database.entity.Reminder
import java.time.Instant

interface ReminderService {
    fun createWorkRequestAndEnqueue(
        context: Context,
        reminder: Reminder?,
        isFirstTime: Boolean = true
    )

    fun postponeRequestAndEnqueue(
        context: Context,
        reminderId: Long,
        time: Instant
    )

    fun cancelWorkRequest(context: Context, tag: String)
}