package com.hadiyarajesh.notex.reminder

import android.content.Context
import com.hadiyarajesh.notex.database.entity.Reminder
import java.time.Instant

interface MainNotificationWorker {


    fun createWorkRequestAndEnqueue(
        context: Context,
        reminder: Reminder?,
        requestType: RequestType = RequestType.OneTimeRequest,
        isFirstTime: Boolean = true
    )

    fun postponeRequestAndEnqueue(
        context: Context,
        reminderId: Long,
        time: Instant
    )

    fun cancelWorkRequest(context: Context, tag: String): Boolean

    companion object {
        enum class RequestType {
            OneTimeRequest,
            PeriodicRequest
        }
    }
}