package com.hadiyarajesh.notex.ui.note.note_folder

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hadiyarajesh.notex.R

@Composable
fun NoteFolderScreen(
    data: List<NoteFolderItem>
) {
//    val data = listOf(
//        NoteFolderItem("Android", 15),
//        NoteFolderItem("Web", 1),
//        NoteFolderItem("Cloud", 21),
//        NoteFolderItem("AWS", 12),
//        NoteFolderItem("Node", 5),
//        NoteFolderItem("Cyber", 151),
//    )
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
                    it.folderId
                }
            ) { noteItem ->
                NoteFolderItemUI(onClick = {}, data = noteItem)
            }
        }
    }
}

@Composable
fun NoteFolderItemUI(
    count: Int = 100,
    onClick: () -> Unit,
    data: NoteFolderItem
) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Item ${data.name}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${data.notes.size} notes",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview
@Composable
private fun NoteFolderScreenPreview() {
    val state = rememberLazyGridState()
    Surface {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Categories",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(start = 120.dp)
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_add_circle_outline),
                    contentDescription = "add_note",
                    modifier = Modifier.padding(end = 10.dp)
                )

            }
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
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Item $noteFolderItems",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "$noteFolderItems notes",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}
