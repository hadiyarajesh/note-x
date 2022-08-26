package com.hadiyarajesh.notex.ui.reminders

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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
