package com.hadiyarajesh.notex.ui.note

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
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
import java.time.Instant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    navController: NavController,
    notesViewModel: NotesViewModel
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val notes = remember { notesViewModel.notes }.collectAsLazyPagingItems()

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = if (notesViewModel.notes == null) {
                Arrangement.Center
            } else
                Arrangement.Top
        ) {
            AllNotesView(
                notes = notes,
                onClick = { note ->

                }
            )
        }
    }
}

@Composable
private fun AllNotesView(
    modifier: Modifier = Modifier,
    notes: LazyPagingItems<Note>,
    onClick: (Note) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        items(notes) { item ->
            item?.let { note ->
                NoteCard(note = note)
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

@Composable
private fun NoteItem(
    modifier: Modifier = Modifier,
    note: Note,
    onClick: (Note) -> Unit,
) {
    Column(modifier = modifier) {
        Text(text = note.title ?: "")
    }
}

@Preview
@Composable
fun NoteItemPreview() {
    Surface {
        NoteItem(
            note = Note(
                noteId = 12345,
                title = "NOte title",
                content = "Note content",
                archived = false,
                createdOn = Instant.now(),
                updatedOn = Instant.now()
            ),
            onClick = {}
        )
    }
}
