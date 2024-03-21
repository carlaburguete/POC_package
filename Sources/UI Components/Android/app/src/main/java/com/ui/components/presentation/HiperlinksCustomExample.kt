package com.ui.components.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.ui.components.components.hyperLinks.HyperlinksText
import com.ui.components.components.hyperLinks.SegmentType
import com.ui.components.components.hyperLinks.TextSegment
import com.ui.components.components.hyperLinks.TextStyleSpan

@Preview(showBackground = true)
@Composable
fun HiperlinksCustomExample() {
    val segments = listOf(
        TextSegment(
            text = "Hola, ",
            type = SegmentType.TEXT,
            styleConfig = TextStyleSpan(color = Color.Red, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        ),
        TextSegment(
            text = "haz clic aquí",
            type = SegmentType.LINK,
            styleConfig = TextStyleSpan(color = Color.Blue, textDecoration = TextDecoration.Underline),
            action = { println("Enlace tocado") }
        ),
        TextSegment(
            text = " para más información.",
            type = SegmentType.TEXT,
            styleConfig = TextStyleSpan(color = Color.Black)
        )
    )

    HyperlinksText(segments = segments)
}

