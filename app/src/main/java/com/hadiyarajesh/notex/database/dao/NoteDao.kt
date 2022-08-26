package com.hadiyarajesh.notex.database.dao

import androidx.paging.PagingSource
import androidx.room.*
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

    @InternalUseOnly
    @Query("UPDATE Note SET parentFolderId = :parentFolderId WHERE noteId = :noteId")
    suspend fun attachNoteToFolder(noteId: Long, parentFolderId: Long)

    @InternalUseOnly
    @Query("UPDATE Folder SET updatedOn = :updatedOn WHERE folderId =:folderId ")
    suspend fun updateFolderModificationProperty(folderId: Long, updatedOn: Instant = Instant.now())

    @Query("SELECT * FROM Note WHERE noteId = :noteId")
    fun getById(noteId: Long): Note

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
    fun markAsArchived(noteId: Long)

    @Delete
    fun delete(note: Note)

    @Query("DELETE FROM Note WHERE noteId = :noteId")
    fun deleteById(noteId: Long)

    /**
     * Permanently delete all notes that are already archived.
     */
    @Query("DELETE FROM Note WHERE archived = 1")
    fun deleteAllArchivedNotes()

    /**
     * WARNING: USE WITH CAUTION
     * Permanently delete all notes
     */
    @Query("DELETE FROM Note")
    fun deleteAll()
}
