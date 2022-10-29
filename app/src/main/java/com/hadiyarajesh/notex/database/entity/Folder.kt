package com.hadiyarajesh.notex.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hadiyarajesh.notex.database.model.FolderType
import com.squareup.moshi.JsonClass
import java.time.Instant

@Entity
@JsonClass(generateAdapter = true)
data class Folder(
    @PrimaryKey(autoGenerate = true)
    val folderId: Long? = null,
    val title: String,
    val description: String?,
    val folderType: FolderType,
    // HexCode of a color
    val color: String? = null,
    val createdOn: Instant,
    val updatedOn: Instant
)
