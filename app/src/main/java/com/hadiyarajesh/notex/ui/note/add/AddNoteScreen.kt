package com.hadiyarajesh.notex.ui.note.add

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector4D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.hadiyarajesh.notex.ui.component.HorizontalSpacer
import com.hadiyarajesh.notex.ui.component.VerticalSpacer
import com.hadiyarajesh.notex.ui.navigation.Screens
import com.hadiyarajesh.notex.ui.note.add.AddNotesViewModel.Companion.noteColors
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen(
    navController: NavController, addNotesViewModel: AddNotesViewModel
) {

    val noteColor = addNotesViewModel.noteColor.value
    val noteBackground = remember {
        Animatable(
            Color(noteColor)
        )
    }
    Scaffold(bottomBar = {
        BottomAppBar(containerColor = noteBackground.value, modifier = Modifier.height(40.dp)) {
            ShowColor(addNotesViewModel = addNotesViewModel, noteBackground)
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
            TopAppBar(navController, addNotesViewModel)
            Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 10.dp, bottom = 1.dp)) {
                BorderLessTextField(
                    text = addNotesViewModel.noteTitle.value.text,
                    hint = addNotesViewModel.noteTitle.value.hint,
                    onValueChange = {
                        addNotesViewModel.onChangeNoteTitle(it)
                    },
                    onFocusChange = {
                        // addNotesViewModel.setTitleHintVisibility(false)
                    },
                    isHintVisible = addNotesViewModel.noteTitle.value.isHintVisible,
                    singleLine = true,
                    textStyle = MaterialTheme.typography.headlineMedium
                )
                VerticalSpacer(size = 16)
                BorderLessTextField(
                    text = addNotesViewModel.noteBody.value.text,
                    hint = addNotesViewModel.noteBody.value.hint,
                    onValueChange = {
                        addNotesViewModel.onChangeNoteBody(it)
                    },
                    onFocusChange = {
                        //addNotesViewModel.setContentHintVisibility(false)
                    },
                    isHintVisible = addNotesViewModel.noteBody.value.isHintVisible,
                    textStyle = MaterialTheme.typography.headlineSmall
                )
            }
        }
    }
}




@Composable
private fun TopAppBar(navController: NavController, addNotesViewModel: AddNotesViewModel) {
    Row(Modifier.fillMaxWidth()) {
        IconButton(onClick = {
            navController.navigate(Screens.Notes.route)
        }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(R.string.cd_back)
            )
        }
        Spacer(Modifier.weight(1f))
        IconButton(onClick = { /* TODO */ }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_notify),
                contentDescription = stringResource(R.string.cd_add)
            )
        }
        IconButton(onClick = {
            addNotesViewModel.saveNote()
            navController.navigate(Screens.Notes.route)

        }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_save_note),
                contentDescription = stringResource(R.string.cd_save)
            )
        }
    }

}

@Composable
fun ShowColor(
    addNotesViewModel: AddNotesViewModel,
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
                        color = if (addNotesViewModel.noteColor.value == colorInt) {
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
                        addNotesViewModel.onChangeColor(colorInt)

                    }
            )
        }
    }


}
