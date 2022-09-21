package com.hadiyarajesh.notex.database.model

import com.hadiyarajesh.notex.utility.Constants

data class NotificationsChannel constructor(
    var channelid: String,
    var channelname: String,
    var channeldescription: String,
    var channelimportance: Int= Constants.DEFAULT_REMINDER_CHANNEL_IMPORTANCE
)