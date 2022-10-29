package com.hadiyarajesh.notex.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.hadiyarajesh.notex.database.entity.Note
import com.hadiyarajesh.notex.utility.InternalUseOnly
import java.time.Instant

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(note: Note)

    /**
     * This method will add note to folder
     */
    @Transaction
    suspend fun addToFolder(noteId: Long, parentFolderId: Long) {
        attachNoteToFolder(noteId, parentFolderId)
        updateFolderModificationProperty(parentFolderId)
    }

    /**
     * This method will update the provided Note and also modify the updatedOn field of the containing Folder(if the note is in a folder)
     */
    @Transaction
    suspend fun updateNote(note: Note, parentFolderId: Long?) {
        insertOrUpdate(note)
        parentFolderId?.let {
            updateFolderModificationProperty(folderId = parentFolderId, updatedOn = note.updatedOn)
        }
    }

    @InternalUseOnly
    @Query("UPDATE Note SET parentFolderId = :parentFolderId WHERE noteId = :noteId")
    suspend fun attachNoteToFolder(noteId: Long, parentFolderId: Long)

    @InternalUseOnly
    @Query("UPDATE Folder SET updatedOn = :updatedOn WHERE folderId =:folderId ")
    suspend fun updateFolderModificationProperty(folderId: Long, updatedOn: Instant = Instant.now())

    @Query("SELECT * FROM Note WHERE noteId = :noteId")
    suspend fun getById(noteId: Long): Note

    /**
     * This method wil only return non-deleted notes.
     * Usually, you'll use this method to get notes
     */
    @Query("SELECT * FROM Note WHERE archived = 0 ORDER BY noteId DESC")
    fun getAllUnArchivedByDesc(): PagingSource<Int, Note>

    /**
     * WARN: USE WITH CAUTION
     * This method wil return ALL notes, including deleted (archived) notes.
     */
    @Query("SELECT * FROM Note ORDER BY noteId DESC")
    fun getAllByDesc(): PagingSource<Int, Note>

    /**
     * Mark a note as deleted (archived)
     */
    @Query("UPDATE Note SET archived = 1 WHERE noteId = :noteId")
    suspend fun markAsArchived(noteId: Long)

    @Query("UPDATE Note SET archived = 0 WHERE noteId = :noteId")
    suspend fun markAsUnarchived(noteId: Long)

    @Delete
    suspend fun delete(note: Note): Int

    @Query("DELETE FROM Note WHERE noteId = :noteId")
    suspend fun deleteById(noteId: Long): Int

    /**
     * Permanently delete all notes that are already archived.
     */
    @Query("DELETE FROM Note WHERE archived = 1")
    suspend fun deleteAllArchivedNotes(): Int

    /**
     * WARNING: USE WITH CAUTION
     * Permanently delete all notes
     */
    @Query("DELETE FROM Note")
    suspend fun deleteAll(): Int
}
