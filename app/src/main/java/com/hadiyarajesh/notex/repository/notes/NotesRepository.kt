package com.hadiyarajesh.notex.repository.notes

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hadiyarajesh.notex.database.dao.NoteDao
import com.hadiyarajesh.notex.database.entity.Note
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow

@Singleton
class NotesRepository @Inject constructor(
    private val noteDao: NoteDao
) {
    suspend fun createNote(
        title: String?,
        content: String?
    ) {
        noteDao.insertOrUpdate(
            Note(
                title = title,
                content = content,
                archived = false,
                archivedOn = null,
                createdOn = Instant.now(),
                updatedOn = Instant.now()
            )
        )
    }

    fun getAllNotes(): Flow<PagingData<Note>> = Pager(
        config = PagingConfig(pageSize = 20)
    ) {
        noteDao.getAllUnArchivedByDesc()
    }.flow

    suspend fun getNote(noteId: Long): Note = noteDao.getById(noteId)

    /**
     * Inserts the provided Note into the database.
     */
    suspend fun addNote(note: Note) = noteDao.insertOrUpdate(note)

    /**
     * Updates the provided Note in the database.
     */
    suspend fun updateNote(note: Note, parentFolderId: Long?) = noteDao.updateNote(note, parentFolderId)

    suspend fun deleteNote(note: Note): Int = noteDao.delete(note)

    suspend fun deleteNote(noteId: Long): Int = noteDao.deleteById(noteId)

    suspend fun deleteArchived(): Int = noteDao.deleteAllArchivedNotes()

    suspend fun deleteAllNotes(): Int = noteDao.deleteAll()

    suspend fun archive(noteId: Long) = noteDao.markAsArchived(noteId)

    suspend fun unarchive(noteId: Long) = noteDao.markAsUnarchived(noteId)

    suspend fun addToFolder(noteId: Long, folderId: Long) = noteDao.addToFolder(noteId, folderId)
}
