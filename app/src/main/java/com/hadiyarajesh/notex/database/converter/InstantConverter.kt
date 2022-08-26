package com.hadiyarajesh.notex.database.converter

import androidx.room.TypeConverter
import java.time.Instant

object InstantConverter {
    @JvmStatic
    @TypeConverter
    fun fromInstant(instant: Instant?): String? = instant?.toString()

    @JvmStatic
    @TypeConverter
    fun toInstant(value: String?): Instant? = value?.let { Instant.parse(value) }
}
