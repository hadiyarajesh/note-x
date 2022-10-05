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
}
