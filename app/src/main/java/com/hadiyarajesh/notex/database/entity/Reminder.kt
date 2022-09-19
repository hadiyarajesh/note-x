package com.hadiyarajesh.notex.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hadiyarajesh.notex.database.model.RepetitionStrategy
import com.squareup.moshi.JsonClass
import java.time.Instant

@Entity
@JsonClass(generateAdapter = true)
data class Reminder(
    @PrimaryKey(autoGenerate = true)
    val reminderId: Long? =null,
    val content: String,
    val reminderTime: Instant,
    val repeat: RepetitionStrategy,
    val cancelled: Boolean,
    val cancelledOn: Instant? = null,
    val completed: Boolean,
    val completedOn: Instant?,
    // HexCode of a color
    val color: String? = null,
    val createdOn: Instant,
    val updatedOn: Instant,
    val parentFolderId: Long? = null
)
