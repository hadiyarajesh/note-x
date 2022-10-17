package com.hadiyarajesh.notex.reminder.notification

data class NotificationDTO(
    val title: String,
    val subTitle: String,
    val reminderId: Long,
    val workerTag: String
)
