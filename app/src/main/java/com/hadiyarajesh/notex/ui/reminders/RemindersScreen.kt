package com.hadiyarajesh.notex.ui.reminders

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.hadiyarajesh.notex.R
import com.hadiyarajesh.notex.database.entity.Reminder
import com.hadiyarajesh.notex.ui.component.EmptyView
import com.hadiyarajesh.notex.ui.component.LoadingProgressBar
import com.hadiyarajesh.notex.ui.component.RetryItem
import com.hadiyarajesh.notex.utility.Constants
import com.hadiyarajesh.notex.utility.singleClickable

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
            Text(
                modifier = Modifier
                    .padding(start = 15.dp, top = 10.dp, bottom = 5.dp)
                    .align(alignment = Alignment.Start),
                text = stringResource(id = R.string.reminders_text),
                color = colorResource(id = R.color.reminder_text_color),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            AllRemindersView(
                reminders = reminders,
                onClick = { reminder ->
                    // TODO Initiate edit reminder flow
                }
            )
        }
    }
}

@Composable
private fun AllRemindersView(
    modifier: Modifier = Modifier,
    reminders: LazyPagingItems<Reminder>,
    onClick: (Reminder) -> Unit,
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 15.dp),
        verticalArrangement = Arrangement.spacedBy(space = 10.dp)
    ) {
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
    reminder: Reminder,
    onClick: (Reminder) -> Unit,
) {
    Card(
        modifier = Modifier
            .background(color = colorResource(id = R.color.white))
            .singleClickable {
                onClick(reminder)
            },
        elevation = CardDefaults.cardElevation(),
        shape = RoundedCornerShape(size = 16.dp)
    ) {
        Column(modifier = Modifier.padding(vertical = 10.dp, horizontal = 12.dp)) {
            Row {
                Text(
                    modifier = Modifier.weight(weight = 1f),
                    text = reminder.content,
                    maxLines = 3,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = colorResource(id = R.color.reminder_text_color)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_delete),
                        contentDescription = Constants.EMPTY_STRING
                    )

                    Text(
                        modifier = Modifier.padding(start = 5.dp),
                        text = "26.08.2022",
                        color = colorResource(id = R.color.reminder_text_color),
                        fontSize = 12.sp
                    )
                }
            }

            Row(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .align(alignment = Alignment.End)
            ) {
                Image(
                    modifier = Modifier.singleClickable {
                        // TODO Handle Delete Click
                    },
                    painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = Constants.EMPTY_STRING
                )

                Image(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .singleClickable {
                            // TODO Handle Edit Click
                        },
                    painter = painterResource(id = R.drawable.ic_edit),
                    contentDescription = Constants.EMPTY_STRING
                )
            }
        }
    }
}
