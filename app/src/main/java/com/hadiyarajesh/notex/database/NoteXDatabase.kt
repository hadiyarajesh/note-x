package com.hadiyarajesh.notex.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hadiyarajesh.notex.database.converter.InstantConverter
import com.hadiyarajesh.notex.database.dao.NoteDao
import com.hadiyarajesh.notex.database.entity.Note

@Database(
    version = 1,
    exportSchema = true,
    entities = [
        Note::class
    ]
)
@TypeConverters(
    InstantConverter::class
)
abstract class NoteXDatabase : RoomDatabase() {
    abstract val noteDao: NoteDao
}
