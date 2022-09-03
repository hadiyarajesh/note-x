package com.hadiyarajesh.notex.ui.folders

import android.graphics.Color
import com.hadiyarajesh.notex.database.entity.Note
import java.time.Instant

data class NoteFolderItem(
    val folderId: Long,
    val name: String? = null,
    val description: String? = null,
    val notes: List<Note>,
    val color: Color? = null,
    val createdOn: Instant,
    val updatedOn: Instant
)
