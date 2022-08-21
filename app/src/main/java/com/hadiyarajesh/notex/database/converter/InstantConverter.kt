package com.hadiyarajesh.notex.database.converter

import androidx.room.TypeConverter
import java.time.Instant

class InstantConverter {
    @TypeConverter
    fun toString(instant: Instant): String {
        return instant.toString()
    }

    @TypeConverter
    fun toInstant(s: String): Instant? {
        return Instant.parse(s)
    }
}
