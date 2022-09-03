package com.hadiyarajesh.notex.database.dao

import androidx.room.*
import com.hadiyarajesh.notex.database.entity.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(note: Note): Long

    @Query("SELECT * FROM Note")
    suspend fun getNotes(): Flow<List<Note>>

    @Delete
    suspend fun deleteNote(note: Note): Note
}
