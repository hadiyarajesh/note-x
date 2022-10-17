package com.hadiyarajesh.notex.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hadiyarajesh.notex.database.converter.InstantConverter
import com.hadiyarajesh.notex.database.entity.Note
import java.time.Instant

@Composable
fun NoteCard(note: Note) {
    Card(
        elevation = CardDefaults.cardElevation(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            note.title?.let {
                TextSemiBold(
                    content = it,
                    null,
                    MaterialTheme.typography.titleLarge, Color.Black
                )
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min)
                        .weight(1f)
                ) {
                    TextSemiBold(
                        content = "Succeed", Modifier.padding(end = 8.dp)
                    )
                    Divider(
                        color = Color.Gray,
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp)
                    )
                    TextSemiBold(content = "Goal", Modifier.padding(start = 8.dp))
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextSemiBold(
                        content = InstantConverter.getLocalDate(note.createdOn).toString()
                    )
                }

            }
        }
    }
}

@Preview
@Composable
fun NoteCardPrev() {
    NoteCard(
        Note(
            noteId = 12345,
            title = "Note title",
            content = "Note content",
            archived = false,
            createdOn = Instant.now(),
            updatedOn = Instant.now()
        )
    )
}
