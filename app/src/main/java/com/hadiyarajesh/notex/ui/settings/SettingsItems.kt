package com.hadiyarajesh.notex.ui.settings

import androidx.annotation.DrawableRes
import com.hadiyarajesh.notex.R

sealed class SettingsItems(
    val name: String,
    @DrawableRes val icon: Int,
) {

    object Notifications : SettingsItems(
        name = "Notifications",
        icon = R.drawable.ic_notification
    )

    object Sounds : SettingsItems(
        name = "Sounds",
        icon = R.drawable.ic_sound
    )

    object Customize : SettingsItems(
        name = "Customize",
        icon = R.drawable.ic_customize
    )

    object Language : SettingsItems(
        name = "Language",
        icon = R.drawable.ic_language
    )

    object Widgets : SettingsItems(
        name = "Widgets",
        icon = R.drawable.ic_widget
    )

    object About : SettingsItems(
        name = "About",
        icon = R.drawable.ic_about
    )

    object Version : SettingsItems(
        name = "Version",
        icon = R.drawable.ic_settings
    )
}