package com.hadiyarajesh.notex.ui


import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.hadiyarajesh.notex.R
import com.hadiyarajesh.notex.database.entity.Reminder
import com.hadiyarajesh.notex.database.model.NotificationsChannel
import com.hadiyarajesh.notex.database.model.RepetitionStrategy
import com.hadiyarajesh.notex.ui.reminders.RemindersViewModel
import com.hadiyarajesh.notex.ui.reminders.StrategyDialog
import com.hadiyarajesh.notex.ui.testreminder.ReminderWorkerBuilder
import com.hadiyarajesh.notex.utility.Constants
import com.hadiyarajesh.notex.utility.NotificationHelper
import com.hadiyarajesh.notex.utility.getTimeDifference
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*

@AndroidEntryPoint
class CreateReminder : ComponentActivity() {
    val remindersViewModel: RemindersViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        remindersViewModel.reminders
        createNotificationChannel()
        setContent {
            NoteXApp()
            val strategy = remember {
                mutableStateOf("")
            }
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                val time = datetimePicker()
                StrategyDialog {
                    createreminder(it, time)
                    strategy.value = "Strategy = ${it.name}"

                }
                Text(text = strategy.value, style = MaterialTheme.typography.headlineSmall)

            }


        }
    }

    private fun createreminder(
        strategy: RepetitionStrategy,
        time: LocalDateTime
    ) {
        val timeInstant = time.toInstant(
            OffsetDateTime.now().offset
        )

        CoroutineScope(Dispatchers.IO).launch {
            val id: Long = remindersViewModel.createReminder(
                "Its time to build a ui in compose",
                timeInstant, strategy
            )

            val reminder = remindersViewModel.getreminderbyid(id)
            val delay=time.getTimeDifference()
            launch(Dispatchers.Main) {
                createWorkRequest(
                    reminder,delay
                )
            }
        }

    }

    private fun createWorkRequest(
        reminder: Reminder,delay:Long
    ) {
        val formattedInstant: String =
            DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a").withZone(ZoneId.systemDefault())
                .format(reminder.reminderTime);
        val data = workDataOf(
            Constants.KEY_REMINDER_ID to reminder.reminderId
        )
        val work = ReminderWorkerBuilder().createOneTimeWorkRequest(
            delay, data, "Reminder Scheduled at: " + formattedInstant.toString()
        )

        WorkManager.getInstance(applicationContext).enqueueUniqueWork(
            reminder.reminderId.toString(), ExistingWorkPolicy.REPLACE,
            work
        )

    }

    private fun createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationHelper.createNotificationchannel(
                NotificationsChannel(
                    getString(R.string.notification_channel_id),
                    getString(R.string.notification_channel_name),
                    getString(R.string.notification_channel_description)
                ), this
            )
        }
    }
}


