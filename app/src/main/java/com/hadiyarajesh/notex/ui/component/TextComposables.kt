package com.hadiyarajesh.notex.ui.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight


@Composable
fun TextSemiBold(
    content: String,
    modifier: Modifier? = Modifier,
    textStyle: TextStyle? = null,
    color: Color? = null
) {
    Text(
        text = content, fontWeight = FontWeight.SemiBold,
        color = color ?: Color.Gray,
        modifier = modifier ?: Modifier,
        style = textStyle ?: TextStyle.Default
    )
}