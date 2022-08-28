package com.hadiyarajesh.notex.ui.note.note_folder

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun NoteFolderScreen(
    data: List<NoteFolderItem>
) {
    val state = rememberLazyGridState()
    Surface {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            state = state,
            modifier = Modifier.padding(8.dp)
        ) {
            items(
                items = data,
                key = {
                    it.title
                }
            ) { noteItem ->
                NoteFolderItemUI(onClick = {}, data = noteItem.title)
            }
        }
    }
}

@Composable
fun NoteFolderItemUI(
    count: Int = 100,
    onClick: () -> Unit,
    data: String
) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = "Item $data"
        )
    }
}

data class NoteFolderItem(
    val title: String
)

@Preview
@Composable
private fun NoteFolderScreenPreview() {
    val state = rememberLazyGridState()
    Surface {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            state = state
        ) {
            items(100) { noteFolderItems ->
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Cente
                ) {
                    Text(
                        textAlign = TextAlign.Center,
                        text = "Item $noteFolderItems"
                    )
                }
            }
        }
    }
}
