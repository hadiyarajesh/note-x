package com.hadiyarajesh.notex.database.converter

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

object InstantConverter {
    @JvmStatic
    @TypeConverter
    fun fromInstant(instant: Instant?): String? = instant?.toString()

    @JvmStatic
    @TypeConverter
    fun toInstant(value: String?): Instant? = value?.let { Instant.parse(value) }

    @JvmStatic
    @TypeConverter
    fun getLocalDate(instant: Instant): LocalDate {
        val localDateTime: LocalDateTime =
            LocalDateTime.ofInstant(instant, ZoneOffset.systemDefault())
        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        return LocalDate.parse(localDateTime.format(formatter), formatter)
    }
}
