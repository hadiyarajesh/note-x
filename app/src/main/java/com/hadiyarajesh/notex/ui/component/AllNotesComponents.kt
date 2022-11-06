package com.hadiyarajesh.notex.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hadiyarajesh.notex.database.converter.InstantConverter
import com.hadiyarajesh.notex.database.entity.Note
import java.time.Instant

@Composable
fun NoteCard(note: Note, onClick: (Note) -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .clickable { onClick(note) },
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            note.title?.let {
                Text(
                    text = it,
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                )
            }

            note.content?.let {
                Text(
                    text = it,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(IntrinsicSize.Min)
//                        .weight(1f)
//                ) {
//                    TextSemiBold(
//                        content = "Succeed", Modifier.padding(end = 8.dp)
//                    )
//                    Divider(
//                        color = Color.Gray,
//                        modifier = Modifier
//                            .fillMaxHeight()
//                            .width(1.dp)
//                    )
//                    TextSemiBold(content = "Goal", Modifier.padding(start = 8.dp))
//                }

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
        ),
        onClick = {}
    )
}
