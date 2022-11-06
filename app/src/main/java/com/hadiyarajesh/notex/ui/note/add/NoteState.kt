package com.hadiyarajesh.notex.ui.note.add

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import com.hadiyarajesh.notex.ui.theme.BabyBlue
import com.hadiyarajesh.notex.ui.theme.LightGreen
import com.hadiyarajesh.notex.ui.theme.LightOrange
import com.hadiyarajesh.notex.ui.theme.LightYellow
import com.hadiyarajesh.notex.ui.theme.RedOrange
import com.hadiyarajesh.notex.ui.theme.RedPink
import com.hadiyarajesh.notex.ui.theme.Violet
import com.hadiyarajesh.notex.ui.theme.White

data class NoteState(
    val title: MutableState<String?> = mutableStateOf(""),
    val noteDesc: MutableState<String?> = mutableStateOf(""),
    val color: MutableState<Int> = mutableStateOf(noteColors[0].toArgb())
) {
    companion object {
        val noteColors =
            listOf(
                White, RedOrange, LightGreen, Violet, BabyBlue, RedPink, LightYellow, LightOrange
            )
    }
}
