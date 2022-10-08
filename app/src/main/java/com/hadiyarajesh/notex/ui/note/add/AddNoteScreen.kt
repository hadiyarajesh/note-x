package com.hadiyarajesh.notex.ui.note.add

import androidx.activity.compose.BackHandler
import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector4D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hadiyarajesh.notex.R
import com.hadiyarajesh.notex.ui.component.BorderLessTextField
import com.hadiyarajesh.notex.ui.component.VerticalSpacer
import com.hadiyarajesh.notex.ui.note.add.NoteState.Companion.noteColors
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen(
    navController: NavController, addNotesViewModel: AddNotesViewModel,
    noteId: Long?
) {
    val state = remember {
        mutableStateOf(NoteState())
    }

    val noteBackground = remember {
        Animatable(
            Color(state.value.color.value)
        )
    }
    LaunchedEffect(key1 = true) {
        noteId?.let { id ->
            val note = addNotesViewModel.getNote(id)
            state.value.apply {
                title.value = note.title ?: ""
                noteDesc.value = note.content ?: ""
                color.value = Color(android.graphics.Color.parseColor(note.color)).toArgb()
            }

            noteBackground.animateTo(
                targetValue = Color(state.value.color.value),
                animationSpec = tween(
                    durationMillis = 400
                )
            )
        }
    }

    BackHandler {
        if (checkNoteIsNotEmpty(noteState = state.value)){
            addNotesViewModel.saveNote(state.value, noteId)
        }
        navController.navigateUp()
    }

    Scaffold(bottomBar = {
        BottomAppBar(containerColor = noteBackground.value, modifier = Modifier.height(40.dp)) {
            ShowColor(state = state, noteBackground)
        }
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(noteBackground.value)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,

            ) {

            Row(Modifier.fillMaxWidth()) {
                IconButton(onClick = {
                    navController.navigateUp()
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.cd_back)
                    )
                }
                Spacer(Modifier.weight(1f))
                IconButton(onClick = {
                    if (checkNoteIsNotEmpty(noteState = state.value)){
                        addNotesViewModel.saveNote(state.value, noteId)
                    }
                    navController.navigateUp()

                }) {
                    AnimatedVisibility(visible = checkNoteIsNotEmpty(state.value)) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_save_note),
                            contentDescription = stringResource(R.string.cd_save)
                        )
                    }
                }
            }

            Column(
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 10.dp,
                    bottom = 1.dp
                )
            ) {
                BorderLessTextField(
                    text = state.value.title.value,
                    hint = stringResource(id = R.string.hint_note_title),
                    onValueChange = {
                        state.value.title.value = it
                    },
                    maxLines = 1,
                    textStyle = MaterialTheme.typography.headlineMedium
                )
                VerticalSpacer(size = 16)
                BorderLessTextField(
                    text = state.value.noteDesc.value,
                    hint = stringResource(id = R.string.hint_note_description),
                    onValueChange = {
                        state.value.noteDesc.value = it
                    },
                    textStyle = MaterialTheme.typography.headlineSmall
                )
            }
        }
    }
}

@Composable
private fun ShowColor(
    state: MutableState<NoteState>,
    noteBackground: Animatable<Color, AnimationVector4D>
) {
    val scope = rememberCoroutineScope()
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        noteColors.forEach { color ->
            val colorInt = color.toArgb()
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .shadow(5.dp, RectangleShape)
                    .clip(RectangleShape)
                    .background(color)
                    .border(
                        width = 1.dp,
                        color = if (state.value.color.value == colorInt) {
                            Color.DarkGray
                        } else Color.Transparent,
                        shape = RectangleShape,
                    )
                    .clickable {
                        scope.launch {
                            noteBackground.animateTo(
                                targetValue = Color(colorInt),
                                animationSpec = tween(
                                    durationMillis = 400
                                )
                            )
                        }
                        state.value.color.value = colorInt

                    }
            )
        }
    }
}
fun checkNoteIsNotEmpty(noteState: NoteState) : Boolean {
    return (noteState.title.value.isNotBlank() && noteState.noteDesc.value.isNotBlank())
}
