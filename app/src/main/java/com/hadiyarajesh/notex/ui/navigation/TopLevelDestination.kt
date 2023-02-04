package com.hadiyarajesh.notex.ui.navigation

import androidx.annotation.DrawableRes
import com.hadiyarajesh.notex.R

sealed class TopLevelDestination(
    val title: String,
    val route: String,
    @DrawableRes val icon: Int,
    @DrawableRes val selectedIcon: Int
) {
    object Notes : TopLevelDestination(
        title = "Notes",
        route = "notes",
        icon = R.drawable.ic_note_outlined,
        selectedIcon = R.drawable.ic_note_filled
    )

    object AddNote : TopLevelDestination(
        title = "AddNote",
        route = "add_note",
        icon = R.drawable.ic_note_outlined,
        selectedIcon = R.drawable.ic_note_filled
    )

    object Folders : TopLevelDestination(
        title = "Folders",
        route = "folders",
        icon = R.drawable.ic_baseline_folder_open,
        selectedIcon = R.drawable.ic_baseline_folder
    )

    object Reminders : TopLevelDestination(
        title = "Reminders",
        route = "reminders",
        icon = R.drawable.ic_task_outlined,
        selectedIcon = R.drawable.ic_task_filled
    )

    object Settings : TopLevelDestination(
        title = "Settings",
        route = "settings",
        icon = R.drawable.ic_task_outlined,
        selectedIcon = R.drawable.ic_task_filled
    )

    object NoteFolder : TopLevelDestination(
        title = "Note Folder",
        route = "note_folder",
        icon = R.drawable.ic_task_outlined,
        selectedIcon = R.drawable.ic_task_filled
    )

    fun withArgs(vararg args: Any): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}

val bottomNavItems = listOf(
    TopLevelDestination.Notes,
    TopLevelDestination.Reminders
)
