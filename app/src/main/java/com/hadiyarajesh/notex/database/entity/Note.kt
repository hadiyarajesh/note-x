package com.hadiyarajesh.notex.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import java.time.Instant

@Entity
@JsonClass(generateAdapter = true)
data class Note(
    @PrimaryKey(autoGenerate = true)
    val noteId: Long? = null,
    val title: String?,
    val content: String?,
    val archived: Boolean,
    val archivedOn: Instant? = null,
    // HexCode of a color
    val color: String? = null,
    val createdOn: Instant,
    val updatedOn: Instant,
    val parentFolderId: Long? = null,
)
