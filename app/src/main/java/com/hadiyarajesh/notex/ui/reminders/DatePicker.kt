package com.hadiyarajesh.notex.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import java.time.LocalDateTime

// Creating a composable
// function to display Top Bar
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainContent() {
    Scaffold(
        content = { datetimePicker() }
    )
}

// Creating a composable function
// to create a Time Picker
// Calling this function as content
// in the above function

@Composable
fun datetimePicker():LocalDateTime {
    val context = LocalContext.current
    val date = remember { mutableStateOf(LocalDateTime.now()) }
    val timepicker = TimePickerDialog(context, { _, hour, minute ->
        date.value = LocalDateTime.of(
            date.value.year,
            date.value.monthValue,
            date.value.dayOfMonth,
            hour,
            minute
        )
    }, date.value.hour, date.value.minute, false)
    val datepicker = DatePickerDialog(context, { _, year, month, day ->
        timepicker.show()

        date.value = LocalDateTime.of(year, month + 1, day, date.value.hour, date.value.minute)
    }, date.value.year, date.value.monthValue - 1, date.value.dayOfMonth)

    Button(onClick = {
        datepicker.show()
    }) {
        Text(text = "Show Date and Time Picker")
    }

    return date.value
}
