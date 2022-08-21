package com.hadiyarajesh.notex.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hadiyarajesh.notex.ui.note.NotesScreen
import com.hadiyarajesh.notex.ui.note.NotesViewModel
import com.hadiyarajesh.notex.ui.reminders.RemindersScreen

@Composable
fun NoteXNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screens.Notes.route
    ) {
        composable(route = Screens.Notes.route) {
            val notesViewModel = hiltViewModel<NotesViewModel>()
            NotesScreen(
                navController = navController,
                notesViewModel = notesViewModel
            )
        }

        composable(route = Screens.Reminders.route) {
            RemindersScreen(
                navController = navController
            )
        }
    }
}
