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

    object Reminders : Screens(
        route = "Reminders",
        icon = R.drawable.ic_task_outlined,
        selectedIcon = R.drawable.ic_task_filled
    )

    object Settings : Screens(
        route = "Settings",
        icon = R.drawable.ic_settings,
        selectedIcon = R.drawable.ic_settings_selected
    )

    object Notifications : Screens(
        route = "Notifications",
        icon = R.drawable.ic_notification,
        selectedIcon = R.drawable.ic_notification
    )

    object Sounds : Screens(
        route = "Sounds",
        icon = R.drawable.ic_sound,
        selectedIcon = R.drawable.ic_sound
    )

    object Customize : Screens(
        route = "Customize",
        icon = R.drawable.ic_customize,
        selectedIcon = R.drawable.ic_customize
    )

    object Language : Screens(
        route = "Language",
        icon = R.drawable.ic_language,
        selectedIcon = R.drawable.ic_language
    )

    object Widgets : Screens(
        route = "Widgets",
        icon = R.drawable.ic_widget,
        selectedIcon = R.drawable.ic_widget
    )

    object About : Screens(
        route = "About",
        icon = R.drawable.ic_about,
        selectedIcon = R.drawable.ic_about
    )

    object Version : Screens(
        route = "Version",
        icon = R.drawable.ic_settings,
        selectedIcon = R.drawable.ic_settings
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
    Screens.Settings
)