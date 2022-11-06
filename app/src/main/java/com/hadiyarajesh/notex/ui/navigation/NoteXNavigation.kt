package com.hadiyarajesh.notex.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
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
        startDestination = Screens.Notes.route
    ) {
        composable(route = Screens.Notes.route) {
            bottomBarState.value = true
            val notesViewModel = hiltViewModel<NotesViewModel>()

            NotesScreen(
                navController = navController,
                notesViewModel = notesViewModel
            )
        }

        composable(
            route = Screens.AddNote.route + "?noteId={noteId}",
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

        composable(route = Screens.Reminders.route) {
            bottomBarState.value = true
            val remindersViewModel = hiltViewModel<RemindersViewModel>()

            RemindersScreen(
                navController = navController,
                remindersViewModel = remindersViewModel
            )
        }

        composable(route = Screens.Folders.route) {
            bottomBarState.value = true
            val noteFolderViewModel = hiltViewModel<NoteFolderViewModel>()

            NoteFolderScreen(
                navController = navController,
                folderViewModel = noteFolderViewModel
            )
        }

        composable(route = Screens.Settings.route) {
            bottomBarState.value = false
        }
    }
}

@Composable
fun MainBottomBar(
    destinations: List<Screens>,
    onNavigateToDestination: (Screens) -> Unit,
    currentDestination: NavDestination?
) {
    NavigationBar(tonalElevation = 0.dp) {
        destinations.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)

            NavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    val icon = if (selected) {
                        destination.selectedIcon
                    } else {
                        destination.icon
                    }

                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = destination.route
                    )
                },
                label = { Text(destination.route) }
            )
        }
    }
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: Screens) =
    this?.hierarchy?.any {
        it.route?.contains(destination.route, true) ?: false
    } ?: false
