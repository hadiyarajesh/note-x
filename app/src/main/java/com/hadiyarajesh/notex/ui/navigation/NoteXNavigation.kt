package com.hadiyarajesh.notex.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hadiyarajesh.notex.ui.folders.NoteFolderScreen
import com.hadiyarajesh.notex.ui.folders.NoteFolderViewModel
import com.hadiyarajesh.notex.ui.note.add.AddNoteScreen
import com.hadiyarajesh.notex.ui.note.add.AddNotesViewModel
import com.hadiyarajesh.notex.ui.note.all.NotesScreen
import com.hadiyarajesh.notex.ui.note.all.NotesViewModel
import com.hadiyarajesh.notex.ui.reminders.RemindersScreen
import com.hadiyarajesh.notex.ui.reminders.RemindersViewModel

@Composable
fun NoteXNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    bottomBarState: MutableState<Boolean>
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = TopLevelDestination.Notes.route
    ) {
        composable(route = TopLevelDestination.Notes.route) {
            bottomBarState.value = true
            val notesViewModel = hiltViewModel<NotesViewModel>()

            NotesScreen(
                navController = navController,
                notesViewModel = notesViewModel
            )
        }

        composable(
            route = TopLevelDestination.AddNote.route + "?noteId={noteId}",
            arguments = listOf(
                navArgument(
                    name = "noteId"
                ) {
                    type = NavType.LongType
                    defaultValue = -1
                }
            )
        ) { backStackEntry ->
            bottomBarState.value = false

            val noteId = backStackEntry.arguments?.getLong("noteId")
            val addNotesViewModel: AddNotesViewModel = hiltViewModel()

            AddNoteScreen(
                navController = navController,
                addNotesViewModel = addNotesViewModel,
                noteId = if (noteId?.compareTo(-1) == 0) null else noteId
            )
        }

        composable(route = TopLevelDestination.Reminders.route) {
            bottomBarState.value = true
            val remindersViewModel = hiltViewModel<RemindersViewModel>()

            RemindersScreen(
                navController = navController,
                remindersViewModel = remindersViewModel
            )
        }

        composable(route = TopLevelDestination.Folders.route) {
            bottomBarState.value = true
            val noteFolderViewModel = hiltViewModel<NoteFolderViewModel>()

            NoteFolderScreen(
                navController = navController,
                folderViewModel = noteFolderViewModel
            )
        }

        composable(route = TopLevelDestination.Settings.route) {
            bottomBarState.value = false
        }
    }
}
