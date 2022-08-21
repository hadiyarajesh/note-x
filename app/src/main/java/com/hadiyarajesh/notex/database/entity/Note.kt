package com.hadiyarajesh.notex.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import java.time.Instant

@Entity
@JsonClass(generateAdapter = true)
data class Note(
    @PrimaryKey
    val noteId: Long,
    val text: String,
    val lastModified: Instant
)
