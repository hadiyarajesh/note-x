package com.hadiyarajesh.notex.ui.note

import android.util.Log
import androidx.lifecycle.ViewModel
import com.hadiyarajesh.notex.repository.NotesRepository
import com.hadiyarajesh.notex.utility.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val notesRepository: NotesRepository
) : ViewModel() {
    init {
        Log.i(TAG, "${this.javaClass.name} initialized")
    }
}
