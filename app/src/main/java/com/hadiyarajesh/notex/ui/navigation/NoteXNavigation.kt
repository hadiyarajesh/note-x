package com.hadiyarajesh.notex.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.hadiyarajesh.notex.ui.folders.FoldersScreen
import com.hadiyarajesh.notex.ui.folders.FoldersViewModel
import com.hadiyarajesh.notex.ui.note.NotesScreen
import com.hadiyarajesh.notex.ui.note.NotesViewModel
import com.hadiyarajesh.notex.ui.note.add.AddNoteScreen
import com.hadiyarajesh.notex.ui.note.add.AddNotesViewModel
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

        composable(route = Screens.AddNote.route) {
            bottomBarState.value = false
            val addNotesViewModel = hiltViewModel<AddNotesViewModel>()
            AddNoteScreen(navController = navController, addNotesViewModel = addNotesViewModel)
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
            val foldersViewModel = hiltViewModel<FoldersViewModel>()

            FoldersScreen(
                navController = navController,
                foldersViewModel = foldersViewModel
            )

        }

        composable(route = Screens.Settings.route) {
            bottomBarState.value = false
        }
    }
}

@Composable
fun MainBottomBar(
    navController: NavController,
    items: List<Screens>,
    onFABClick: () -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    BottomAppBar(
        actions = {
            items.forEach { screen ->
                val selected = navBackStackEntry?.destination?.route == screen.route

                IconButton(
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(id = if (selected) screen.selectedIcon else screen.icon),
                        contentDescription = screen.route
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onFABClick) {
                Icon(imageVector = Icons.Outlined.Add, contentDescription = null)
            }
        }
    )
}
