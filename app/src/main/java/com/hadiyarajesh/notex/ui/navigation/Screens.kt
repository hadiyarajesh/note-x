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
        icon = R.drawable.ic_launcher_foreground,
        selectedIcon = R.drawable.ic_launcher_foreground
    )

    object Reminders : Screens(
        route = "Reminders",
        icon = R.drawable.ic_launcher_foreground,
        selectedIcon = R.drawable.ic_launcher_foreground
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
