package com.hadiyarajesh.notex.ui.note.add

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadiyarajesh.notex.database.dao.NoteDao
import com.hadiyarajesh.notex.database.entity.Note
import com.hadiyarajesh.notex.ui.theme.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class AddNotesViewModel @Inject constructor(private val noteDao: NoteDao) : ViewModel() {

    private var _noteTitle = mutableStateOf(NoteTextFieldState(hint = "Title"))
    var noteTitle: State<NoteTextFieldState> = _noteTitle

    private var _noteBody = mutableStateOf(NoteTextFieldState(hint = "Note Description"))
    var noteBody: State<NoteTextFieldState> = _noteBody

    private val _noteColor = mutableStateOf(noteColors.random().toArgb())
    val noteColor: State<Int> = _noteColor

    fun onChangeNoteTitle(text: String) {
        _noteTitle.value = NoteTextFieldState(text = text)
    }

    fun onChangeNoteBody(text: String) {
        _noteBody.value = NoteTextFieldState(text = text)
    }

    fun onChangeColor(color: Int) {
        _noteColor.value = color
    }

    fun setTitleHintVisibility(hint : Boolean) {
        _noteTitle.value = NoteTextFieldState(isHintVisible = hint)
    }

    fun setContentHintVisibility(hint : Boolean) {
        _noteBody.value = NoteTextFieldState(isHintVisible = hint)
    }

    companion object {
        val noteColors =
            listOf(White, RedOrange, LightGreen, Violet, BabyBlue, RedPink, LightYellow, LightOrange)
    }

    fun saveNote() {
        viewModelScope.launch {
            noteDao.insertOrUpdate(
                note = Note(
                    title = noteTitle.value.text,
                    content = noteBody.value.text,
                    archived = false,
                    createdOn = Instant.now(),
                    updatedOn = Instant.now()
                )
            )
        }


    }
}