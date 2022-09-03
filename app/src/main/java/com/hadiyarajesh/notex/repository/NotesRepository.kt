package com.hadiyarajesh.notex.repository

import com.hadiyarajesh.notex.database.dao.NoteDao
import com.hadiyarajesh.notex.database.entity.Note
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotesRepository @Inject constructor(private val noteDao: NoteDao) {


    suspend fun add(note: Note): Long = noteDao.insertOrUpdate(note)

    suspend fun update(note: Note): Long = noteDao.insertOrUpdate(note)

    suspend fun remove(note: Note): Note = noteDao.deleteNote(note)

    suspend fun allNotes(): Flow<List<Note>> = noteDao.getNotes()
}
