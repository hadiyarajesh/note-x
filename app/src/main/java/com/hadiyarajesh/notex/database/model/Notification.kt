package com.hadiyarajesh.notex.database.model

import com.hadiyarajesh.notex.R

data class Notification(
    var notificationid: Int,
    var channelid: String,
    var title: String,
    var content: String,
    var priority: Int,
    var icon: Int= R.drawable.ic_note_outlined,

    )