package com.hadiyarajesh.notex.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun NoteCard(modifier: Modifier) {
    Card(
        elevation = CardDefaults.cardElevation(),
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(18.dp)
        ) {
            Text(
                text = "Hello Compose",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
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
                    Text(
                        text = "Succeed",
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Gray,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                    Divider(
                        color = Color.Gray, modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp)
                    )
                    Text(
                        text = "Goal", fontWeight = FontWeight.SemiBold,
                        color = Color.Gray, modifier = Modifier.padding(start = 16.dp)
                    )
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f), horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = "2020/05/09", fontWeight = FontWeight.SemiBold,
                        color = Color.Gray
                    )
                }

            }
        }
    }

}

@Preview
@Composable
fun NoteCardPrev() {
    NoteCard(Modifier.padding(16.dp))
}