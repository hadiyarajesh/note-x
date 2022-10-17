package com.hadiyarajesh.notex.ui.note.add

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import com.hadiyarajesh.notex.ui.theme.*

data class NoteState(
    val title: MutableState<String> = mutableStateOf(""),
    val noteDesc: MutableState<String> = mutableStateOf(""),
    val color: MutableState<Int> =  mutableStateOf(noteColors[0].toArgb())
){
    companion object {
        val noteColors =
            listOf(White, RedOrange, LightGreen, Violet, BabyBlue, RedPink, LightYellow, LightOrange)
    }

}




