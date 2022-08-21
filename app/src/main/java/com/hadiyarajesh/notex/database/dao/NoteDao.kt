package com.hadiyarajesh.notex.database.dao

import androidx.room.*
import com.hadiyarajesh.notex.database.entity.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(note: Note)

    @Query("SELECT * FROM Note")
    fun getNote(): Flow<Note>

    @Delete
    fun deleteNote(note: Note)
}
