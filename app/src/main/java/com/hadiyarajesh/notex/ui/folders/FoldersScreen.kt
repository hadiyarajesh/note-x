package com.hadiyarajesh.notex.ui.folders

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.hadiyarajesh.notex.database.entity.Folder
import com.hadiyarajesh.notex.database.model.NoteFolder
import com.hadiyarajesh.notex.database.model.ReminderFolder


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoldersScreen(
    navController: NavController,
    foldersViewModel: FoldersViewModel
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val notesFolders = remember { foldersViewModel.noteFolders }.collectAsLazyPagingItems()
    val reminderFolders = remember { foldersViewModel.reminderFolders }.collectAsLazyPagingItems()

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AllFoldersView(onClick = {})
        }

    }

}



@Composable
private fun AllFoldersView(
   modifier: Modifier = Modifier,
    noteFolders: LazyPagingItems<NoteFolder>? = null,
    reminderFolder: LazyPagingItems<ReminderFolder>? = null,
    onClick: (Folder) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Folders Screen",
            modifier = Modifier.fillMaxWidth()
        )

    }
}

@Preview(
    name = "All Folders Preview",
    showBackground = true,
    showSystemUi = true,
    device = Devices.PIXEL_2_XL
)
@Composable
fun AllFoldersViewPreview(){
    AllFoldersView(onClick = {})
}