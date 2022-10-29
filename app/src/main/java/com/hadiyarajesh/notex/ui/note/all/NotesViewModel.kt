package com.hadiyarajesh.notex.ui.note.all

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hadiyarajesh.notex.database.entity.Note
import com.hadiyarajesh.notex.repository.notes.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val notesRepository: NotesRepository
) : ViewModel() {

//    private val _notesFlow = MutableStateFlow<PagingData<Note>>(PagingData.empty())
//    val notesFlow: StateFlow<PagingData<Note>>
//        get() = _notesFlow

    // Get all notes as soon as collector collect this flow
    val notes: Flow<PagingData<Note>> =
        notesRepository
            .getAllNotes()
            .cachedIn(viewModelScope)

    private var _numberOfNotesDeleted: Int = 0
    val numberOfNotesDeleted: Int
        get() = _numberOfNotesDeleted

    private var _note = MutableStateFlow<Note?>(null)
    val note: StateFlow<Note?>
        get() = _note

    fun createNote(
        title: String?,
        content: String?
    ) = viewModelScope.launch {
        notesRepository.createNote(title = title, content = content)
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            _numberOfNotesDeleted = notesRepository.deleteNote(note)
        }
    }

    fun deleteNote(noteId: Long) {
        viewModelScope.launch {
            _numberOfNotesDeleted = notesRepository.deleteNote(noteId)
        }
    }

    fun deleteArchivedNotes() {
        viewModelScope.launch {
            _numberOfNotesDeleted = notesRepository.deleteArchived()
        }
    }

    /**
     * Deletes all Notes
     */
    fun deleteAll() {
        viewModelScope.launch {
            _numberOfNotesDeleted = notesRepository.deleteAllNotes()
        }
    }

    fun archive(noteId: Long) {
        viewModelScope.launch {
            notesRepository.archive(noteId)
        }
    }

    fun unarchive(noteId: Long) {
        viewModelScope.launch {
            notesRepository.unarchive(noteId)
        }
    }


    fun getNote(noteId: Long) {
        viewModelScope.launch {
            _note.value = notesRepository.getNote(noteId)
        }
    }

    fun addToFolder(noteId: Long,folderId: Long) {
        viewModelScope.launch {
            notesRepository.addToFolder(noteId,folderId)
        }
    }
}
