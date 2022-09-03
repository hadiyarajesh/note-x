package com.hadiyarajesh.notex.ui.note

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadiyarajesh.notex.database.entity.Note
import com.hadiyarajesh.notex.repository.NotesRepository
import com.hadiyarajesh.notex.utility.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val notesRepository: NotesRepository
) : ViewModel() {
    init {
        Log.i(TAG, "${this.javaClass.name} initialized")
    }

    lateinit var notesFlow: StateFlow<List<Note>>



    init {
        getAllNotes()
    }

    private fun getAllNotes() {
        viewModelScope.launch {
            notesFlow = notesRepository.allNotes().stateIn(
                scope = viewModelScope,
                started = WhileSubscribed(5000),
                initialValue = emptyList()
            )
        }
    }

    fun add(note: Note) {
        viewModelScope.launch {
            notesRepository.add(note)
        }
    }

    fun update(note: Note) {
        viewModelScope.launch {
            notesRepository.update(note)
        }
    }

    fun remove(note: Note) {
        viewModelScope.launch {
            notesRepository.remove(note)
        }
    }

}
