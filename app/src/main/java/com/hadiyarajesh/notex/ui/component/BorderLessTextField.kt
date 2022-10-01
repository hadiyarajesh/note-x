package com.hadiyarajesh.notex.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import com.hadiyarajesh.notex.ui.theme.hintColor

@Composable
fun BorderLessTextField(
    text: String,
    hint: String,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = TextStyle(),
    maxLines: Int = Int.MAX_VALUE,
) {
    BasicTextField(
        value = text,
        onValueChange = onValueChange,
        maxLines = maxLines,
        textStyle = textStyle,
        cursorBrush = SolidColor(hintColor),
        modifier = Modifier
            .fillMaxWidth(),
        decorationBox = { inlineTextField ->
            AnimatedVisibility(visible = text.isBlank()) {
                Text(
                    text = hint,
                    color = hintColor,
                    style = textStyle
                )
            }
            inlineTextField()
        }
    )

}