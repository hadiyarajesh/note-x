package com.hadiyarajesh.notex.ui.note.all

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import com.hadiyarajesh.notex.database.entity.Note
import com.hadiyarajesh.notex.ui.component.EmptyView
import com.hadiyarajesh.notex.ui.component.LoadingProgressBar
import com.hadiyarajesh.notex.ui.component.NoteCard
import com.hadiyarajesh.notex.ui.component.RetryItem
import com.hadiyarajesh.notex.ui.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    navController: NavController,
    notesViewModel: NotesViewModel
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val notes = remember { notesViewModel.notes }.collectAsLazyPagingItems()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screens.AddNote.route)
                }
            ) {
                Icon(imageVector = Icons.Outlined.Add, contentDescription = null)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AllNotesView(
                notes = notes,
                onClick = { note ->
                    navController.navigate(Screens.AddNote.route + "?noteId=${note.noteId}")
                }
            )
        }
    }
}

@SuppressWarnings("OptionalWhenBraces")
@Composable
private fun AllNotesView(
    modifier: Modifier = Modifier,
    notes: LazyPagingItems<Note>,
    onClick: (Note) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        items(notes) { item ->
            item?.let { note ->
                NoteCard(note = note, onClick = onClick)
            }
        }

        notes.apply {
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

                loadState.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && notes.itemCount < 1 -> {
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
