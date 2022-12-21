package com.hadiyarajesh.notex.ui.folders

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.hadiyarajesh.notex.R
import com.hadiyarajesh.notex.database.entity.Folder
import com.hadiyarajesh.notex.database.model.FolderType.NoteFolder
import com.hadiyarajesh.notex.ui.navigation.Screens
import java.time.Instant

@Composable
fun NoteFolderScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    folderViewModel: NoteFolderViewModel
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val folders = remember { folderViewModel.getFolders() }.collectAsLazyPagingItems()

    val data = listOf(
        Folder(
            folderId = 12345,
            title = "Android",
            description = "Android Notes",
            folderType = NoteFolder,
            createdOn = Instant.now(),
            updatedOn = Instant.now()
        ),
        Folder(
            folderId = 12346,
            title = "Android1",
            description = "Android Notes",
            folderType = NoteFolder,
            createdOn = Instant.now(),
            updatedOn = Instant.now()
        ),
        Folder(
            folderId = 12347,
            title = "Android2",
            description = "Android Notes",
            folderType = NoteFolder,
            createdOn = Instant.now(),
            updatedOn = Instant.now()
        ),
        Folder(
            folderId = 12348,
            title = "Android3",
            description = "Android Notes",
            folderType = NoteFolder,
            createdOn = Instant.now(),
            updatedOn = Instant.now()
        ),
        Folder(
            folderId = 12349,
            title = "Android4",
            description = "Android Notes",
            folderType = NoteFolder,
            createdOn = Instant.now(),
            updatedOn = Instant.now()
        )
    )
    val state = rememberLazyGridState()
    Surface {
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(id = R.string.categories),
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = modifier.padding(start = 120.dp)
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_add_circle_outline),
                    contentDescription = "add_note",
                    modifier = modifier
                        .padding(end = 10.dp)
                        .clickable {
                            navController.navigate(Screens.AddNote.route)
                        }
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.list_categories),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            LazyVerticalGrid(
                modifier = modifier.padding(8.dp),
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                state = state,
            ) {
                items(
                    items = data,
                    key = {
                        it.title
                    }
                ) { noteItem ->
                    NoteFolderItemUI(onClick = {}, folder = noteItem)
                }
            }
        }
    }
}

@Composable
fun NoteFolderItemUI(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    folder: Folder,
) {
    Box(
        modifier = modifier
            .padding(8.dp)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(painterResource(id = R.drawable.ic_folder_filled), contentDescription = null)

            Text(
                text = "Item ${folder.title}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "${folder.description}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview
@Composable
private fun NoteFolderScreenPreview() {
    val state = rememberLazyGridState()
    Surface {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.categories),
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(start = 130.dp)
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_add_circle_outline),
                    contentDescription = "add_note",
                    modifier = Modifier
                        .padding(end = 10.dp)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.list_categories),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                state = state
            ) {
                items(100) { noteFolderItems ->
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.LightGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                painterResource(id = R.drawable.ic_folder_filled),
                                contentDescription = null
                            )

                            Text(
                                text = "Item $noteFolderItems",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = "$noteFolderItems item description",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}
