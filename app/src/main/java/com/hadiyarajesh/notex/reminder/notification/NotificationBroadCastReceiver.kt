package com.hadiyarajesh.notex.reminder.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.annotation.CallSuper
import com.hadiyarajesh.notex.R
import com.hadiyarajesh.notex.database.dao.ReminderDao
import com.hadiyarajesh.notex.reminder.worker.ReminderWorkManager
import dagger.hilt.android.AndroidEntryPoint
import java.time.Instant
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@AndroidEntryPoint
class NotificationBroadCastReceiver : HiltBroadcastReceiver() {

    @Inject
    lateinit var reminderDao: ReminderDao

    private val reminderWorkManager = ReminderWorkManager()

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action == context.resources.getString(R.string.done_action)) {
            reminderWorkManager.cancelWorkRequest(
                context,
                context.resources.getString(R.string.worker_tag)
            )
        } else if (intent.action == context.resources.getString(R.string.postpone_action)) {

            reminderWorkManager.cancelWorkRequest(
                context,
                context.resources.getString(R.string.worker_tag)
            )

            reminderWorkManager.createWorkRequestAndEnqueue(
                context,
                time = Instant.now().plus(1, ChronoUnit.HOURS),
                isFirstTime = false,
                reminderId = intent.getLongExtra(
                    context.resources.getString(R.string.reminder_id),
                    -1
                )
            )
        }
    }
}

abstract class HiltBroadcastReceiver : BroadcastReceiver() {
    @CallSuper
    override fun onReceive(context: Context, intent: Intent) {
    }
}
