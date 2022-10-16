package com.hadiyarajesh.notex.ui.reminders

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.os.Build
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.hadiyarajesh.notex.R
import com.hadiyarajesh.notex.database.entity.Reminder
import com.hadiyarajesh.notex.database.model.RepetitionStrategy
import com.hadiyarajesh.notex.ui.component.EmptyView
import com.hadiyarajesh.notex.ui.component.LoadingProgressBar
import com.hadiyarajesh.notex.ui.component.RetryItem
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemindersScreen(
    navController: NavController,
    remindersViewModel: RemindersViewModel
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val reminders = remember { remindersViewModel.reminders }.collectAsLazyPagingItems()

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AllRemindersView(
                reminders = reminders,
                onClick = { reminder ->
                }
            )
        }
    }
}

@SuppressWarnings("OptionalWhenBraces")
@Composable
private fun AllRemindersView(
    modifier: Modifier = Modifier,
    reminders: LazyPagingItems<Reminder>,
    onClick: (Reminder) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        items(reminders) { item ->
            item?.let { reminder ->
                ReminderItem(
                    reminder = reminder,
                    onClick = onClick
                )
            }
        }

        reminders.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { LoadingProgressBar(modifier = Modifier.fillParentMaxSize()) }
                }
                loadState.append is LoadState.Loading -> {
                    item {
                        LoadingProgressBar(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    }
                }

                loadState.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && reminders.itemCount < 1 -> {
                    item {
                        EmptyView(
                            modifier = Modifier
                                .fillParentMaxSize()
                                .padding(8.dp),
                            text = stringResource(id = R.string.empty_message),
                        )
                    }
                }

                loadState.refresh is LoadState.Error -> {
                    item {
                        RetryItem(
                            modifier = Modifier.fillMaxSize(),
                            onRetryClick = { retry() }
                        )
                    }
                }
                loadState.append is LoadState.Error -> {
                    item {
                        RetryItem(
                            modifier = Modifier.fillMaxSize(),
                            onRetryClick = { retry() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ReminderItem(
    modifier: Modifier = Modifier,
    reminder: Reminder,
    onClick: (Reminder) -> Unit,
) {
    Column(modifier = modifier) {
        Text(text = reminder.content ?: "")
    }
}


@Composable
fun myContent(): LocalDateTime {

    val reminderViewModel = hiltViewModel<RemindersViewModel>()

    // Fetching local context
    val mContext = LocalContext.current

    // Declaring and initializing a calendar
    val mCalendar = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LocalDateTime.now()
    } else {
        // TODO("VERSION.SDK_INT < O")
    }
    val mHour = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        mCalendar.hour
    } else {
        // TODO("VERSION.SDK_INT < O")
    }
    val mMinute = mCalendar.minute
    // Value for storing time as a string
    val mTime = remember { mutableStateOf("") }
    val mTimeStore = remember { mutableStateOf(mCalendar) }

    // Creating a TimePicker dialog
    val mTimePickerDialog = TimePickerDialog(
        mContext,
        { _, mHour: Int, mMinute: Int ->
            run {
                mTime.value = "$mHour:$mMinute"
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    mTimeStore.value =
                        LocalDateTime.of(
                            mCalendar.toLocalDate(), LocalTime.of(mHour, mMinute)
                        )

                    reminderViewModel.createReminder(
                        "Hello",
                        Instant.now(),
                        repeat = RepetitionStrategy.Daily,
                        context = mContext
                    )
                }
            }
        }, mHour, mMinute, false
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // On button click, TimePicker is
        // displayed, user can select a time
        Button(
            onClick = { mTimePickerDialog.show() },
            colors = ButtonDefaults.buttonColors()
        ) {
            Text(text = "Open Time Picker", color = Color.White)
        }

        // Add a spacer of 100dp
        Spacer(modifier = Modifier.size(100.dp))

        // Display selected time
        Text(text = "Selected Time: ${mTime.value}", fontSize = 30.sp)
    }
    return mTimeStore.value
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Scaffold(
        content = { myContent() }
    )
}
