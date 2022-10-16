package com.hadiyarajesh.notex.reminder.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.annotation.CallSuper
import androidx.core.app.NotificationManagerCompat
import com.hadiyarajesh.notex.R
import com.hadiyarajesh.notex.reminder.worker.ReminderWorkManager
import dagger.hilt.android.AndroidEntryPoint
import java.time.Instant
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@AndroidEntryPoint
class NotificationBroadCastReceiver : HiltBroadcastReceiver() {

    companion object {
        const val TAG = "NotificationBroadCast"
    }

    @Inject
    lateinit var reminderWorkManager: ReminderWorkManager

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        when (intent.action) {
            context.resources.getString(R.string.done_action) ->
                reminderWorkManager.cancelWorkRequest(
                    context,
                    intent.getStringExtra(context.resources.getString(R.string.worker_tag)) ?: ""
                )
            context.resources.getString(R.string.postpone_action) -> {
                reminderWorkManager.cancelWorkRequest(
                    context,
                    intent.getStringExtra(context.resources.getString(R.string.worker_tag)) ?: ""
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
            else -> Log.i(TAG, "Nothing to Perform this Action ${intent.action}")
        }

        with(NotificationManagerCompat.from(context)) {
            cancel(intent.getIntExtra(context.resources.getString(R.string.notification_id), -1))
        }
    }
}

abstract class HiltBroadcastReceiver : BroadcastReceiver() {
    @SuppressWarnings("EmptyFunctionBlock")
    @CallSuper
    override fun onReceive(context: Context, intent: Intent) {
    }
}
