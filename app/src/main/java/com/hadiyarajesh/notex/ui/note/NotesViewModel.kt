package com.hadiyarajesh.notex.ui.note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hadiyarajesh.notex.database.entity.Note
import com.hadiyarajesh.notex.repository.notes.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val notesRepository: NotesRepository
) : ViewModel() {
    // Get all notes as soon as collector collect this flow
    val notes: Flow<PagingData<Note>> =
        notesRepository
            .getAllNotes()
            .cachedIn(viewModelScope)

    fun createNote(
        title: String?,
        content: String?
    ) = viewModelScope.launch {
        notesRepository.createNote(title = title, content = content)
    }
}
