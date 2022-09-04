package com.hadiyarajesh.notex.widgets.reminder

import com.hadiyarajesh.notex.database.entity.Reminder
import com.squareup.moshi.JsonClass

sealed interface ReminderData {

    object Loading : ReminderData

    @JsonClass(generateAdapter = true)
    data class Available(
        val reminders: List<Reminder>
    ) : ReminderData

    @JsonClass(generateAdapter = true)
    data class Unavailable(val message: String) : ReminderData
}