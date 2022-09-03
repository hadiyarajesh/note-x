package com.hadiyarajesh.notex.ui.navigation

import androidx.annotation.DrawableRes
import com.hadiyarajesh.notex.R

sealed class Screens(
    val route: String,
    @DrawableRes val icon: Int,
    @DrawableRes val selectedIcon: Int
) {
    object Notes : Screens(
        route = "Notes",
        icon = R.drawable.ic_note_outlined,
        selectedIcon = R.drawable.ic_note_filled
    )

    object AddNote : Screens(
        route = "AddNote",
        icon = R.drawable.ic_note_outlined,
        selectedIcon = R.drawable.ic_note_filled
    )

    object Folders:Screens(
        route = "Folders",
        icon = R.drawable.ic_baseline_folder_open,
        selectedIcon = R.drawable.ic_baseline_folder
    )

    object Reminders : Screens(
        route = "Reminders",
        icon = R.drawable.ic_task_outlined,
        selectedIcon = R.drawable.ic_task_filled
    )

    object Settings : Screens(
        route = "Settings",
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
    Screens.Notes,
    Screens.Reminders,
    Screens.Folders
)