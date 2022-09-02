package com.hadiyarajesh.notex.ui.testreminder

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.hadiyarajesh.notex.databinding.ActivityReminderBinding
import com.hadiyarajesh.notex.workmanager.ReminderWorker
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class ReminderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReminderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReminderBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnpickup.setOnClickListener {
            showDateTimePicker()
        }
        binding.btnsetreminder.setOnClickListener {

            val todayDateTime = Calendar.getInstance()

            var delayInSeconds = (date.timeInMillis) - todayDateTime.timeInMillis

            Log.i("MYTAG", "delay=$delayInSeconds")

            val formatter = SimpleDateFormat("dd.MM.yyyy hh:mm aa")

            val fdate: String = formatter.format(date.timeInMillis)
            binding.scheduledtime.setText(fdate)
            createWorkRequest(
                "Ui Design",
                "Its time to build notifications design and delete notes design in Jetpack compose",
                delayInSeconds,
                3
            )
        }
    }


    private fun createWorkRequest(
        title: String,
        message: String,
        timeDelayInSeconds: Long,
        notiid: Int
    ) {
        val myWorkRequest: OneTimeWorkRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(timeDelayInSeconds, TimeUnit.MILLISECONDS)
            .setInputData(
                workDataOf(
                    "title" to title,
                    "message" to message,
                    "notiid" to notiid


                )
            )
            .build()

        WorkManager.getInstance(this).enqueueUniqueWork(
            "Reminder Worker", ExistingWorkPolicy.REPLACE,
            myWorkRequest
        )
    }


    lateinit var date: Calendar


    fun showDateTimePicker() {
        val currentDate: Calendar = Calendar.getInstance()
        date = Calendar.getInstance()
        DatePickerDialog(
            this,
            { view, year, monthOfYear, dayOfMonth ->
                date.set(year, monthOfYear, dayOfMonth)
                TimePickerDialog(
                    this,
                    { view, hourOfDay, minute ->
                        date.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        date.set(Calendar.MINUTE, minute)
                        Log.v("TAG", "The choosen one " + date.getTime())

                    },
                    currentDate.get(Calendar.HOUR_OF_DAY),
                    currentDate.get(Calendar.MINUTE),
                    false
                ).show()
            },
            currentDate.get(Calendar.YEAR),
            currentDate.get(Calendar.MONTH),
            currentDate.get(Calendar.DATE)
        ).show()
    }
}
