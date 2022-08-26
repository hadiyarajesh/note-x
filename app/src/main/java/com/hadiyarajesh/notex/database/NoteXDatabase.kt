package com.hadiyarajesh.notex.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hadiyarajesh.notex.database.converter.InstantConverter
import com.hadiyarajesh.notex.database.dao.FolderDao
import com.hadiyarajesh.notex.database.dao.NoteDao
import com.hadiyarajesh.notex.database.dao.ReminderDao
import com.hadiyarajesh.notex.database.entity.Folder
import com.hadiyarajesh.notex.database.entity.Note
import com.hadiyarajesh.notex.database.entity.Reminder

@Database(
    version = 1,
    exportSchema = true,
    entities = [
        Note::class,
        Reminder::class,
        Folder::class
    ]
)
@TypeConverters(
    InstantConverter::class
)
abstract class NoteXDatabase : RoomDatabase() {
    abstract val noteDao: NoteDao
    abstract val reminderDao: ReminderDao
    abstract val folderDao: FolderDao
}
