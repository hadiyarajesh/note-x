package com.hadiyarajesh.notex.database.model

import androidx.room.Embedded
import androidx.room.Relation
import com.hadiyarajesh.notex.database.entity.Folder
import com.hadiyarajesh.notex.database.entity.Reminder

data class ReminderFolder(
    @Embedded
    val folder: Folder,
    @Relation(
        parentColumn = "folderId",
        entityColumn = "parentFolderId"
    )
    val items: List<Reminder>
)
