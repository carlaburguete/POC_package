package com.ui.components.components.hyperLinks

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontSynthesis
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.LocaleList
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextGeometricTransform
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

enum class SegmentType {
    TEXT, LINK
}

data class TextSegment(
    val text: String,
    val type: SegmentType,
    val styleConfig: TextStyleSpan? = null,
    val action: (() -> Unit)? = null
)

data class TextStyleSpan(
    val color: Color? = null,
    val fontSize: TextUnit? = null,
    val fontWeight: FontWeight? = null,
    val fontStyle: FontStyle? = null,
    val fontSynthesis: FontSynthesis? = null,
    val fontFamily: FontFamily? = null,
    val fontFeatureSettings: String? = null,
    val letterSpacing: TextUnit = TextUnit.Unspecified,
    val baselineShift: BaselineShift? = null,
    val textGeometricTransform: TextGeometricTransform? = null,
    val localeList: LocaleList? = null,
    val background: Color = Color.Unspecified,
    val textDecoration: TextDecoration? = null,
    val shadow: Shadow? = null,
)

/**
 * A composable function that displays a text with clickable hyperlinks. This function supports
 * customization of the text appearance, including font size, color, and actions triggered upon
 * clicking different segments of the text. It's designed to handle text that contains multiple
 * segments, where each segment can have its own styling and action, particularly useful for
 * displaying hyperlinks within a larger body of text.
 *
 * @param modifier Modifier for the text, allowing customization of padding, margins, click effects, etc.
 * @param segments A list of TextSegment objects, each representing a segment of the text with its own
 * style and optional action. Segments can be plain text or hyperlinks.
 */
@Composable
fun HyperlinksText(
    modifier: Modifier? = Modifier,
    segments: List<TextSegment>,
) {
    val annotatedString = buildAnnotatedString {
        segments.forEach { segment ->
            val style = segment.styleConfig?.toSpanStyle(defaultFontSize) ?: SpanStyle(
                color = Color.Black,
                fontSize = defaultFontSize
            )
            withStyle(style = style) {
                append(segment.text)
            }
        }
    }

    modifier?.let {
        Box(modifier = modifier) {
            ClickableText(
                text = annotatedString,
                onClick = { offset ->
                    var start = 0
                    segments.forEach { segment ->
                        val end = start + segment.text.length
                        if (offset in start until end && segment.type == SegmentType.LINK) {
                            segment.action?.invoke()
                        }
                        start = end
                    }
                }
            )
        }

    }


}

/**
 * Converts a TextStyleSpan object to a SpanStyle, applying a default font size if none is specified.
 * This function is primarily used within the HyperlinksText composable to convert individual segment
 * styles to SpanStyles that can be applied to parts of an AnnotatedString.
 *
 * @param defaultFontSize The default font size to use if the TextStyleSpan does not specify a font size.
 * Provides a fallback font size to ensure text segments have a consistent appearance.
 * @return A SpanStyle object that represents the styling information contained in this TextStyleSpan,
 * including color, font size, weight, style, and other text attributes. This SpanStyle can be applied
 * to parts of an AnnotatedString.
 */
fun TextStyleSpan.toSpanStyle(defaultFontSize: TextUnit): SpanStyle {
    return SpanStyle(
        color = this.color ?: Color.Unspecified,
        fontSize = this.fontSize ?: defaultFontSize,
        fontWeight = this.fontWeight,
        fontStyle = this.fontStyle,
        fontSynthesis = this.fontSynthesis,
        fontFamily = this.fontFamily,
        fontFeatureSettings = this.fontFeatureSettings,
        letterSpacing = this.letterSpacing,
        baselineShift = this.baselineShift,
        textGeometricTransform = this.textGeometricTransform,
        localeList = this.localeList,
        background = this.background,
        textDecoration = this.textDecoration,
        shadow = this.shadow
    )
}

@Preview(showBackground = true)
@Composable
fun MyScreen() {
    val segments = listOf(
        TextSegment(
            text = "Hola, ",
            type = SegmentType.TEXT,
            styleConfig = TextStyleSpan(
                color = Color.Red,
                fontSize = 29.sp,
                fontWeight = FontWeight.Bold
            )
        ),
        TextSegment(
            text = "haz clic aquí",
            type = SegmentType.LINK,
            styleConfig = TextStyleSpan(
                color = Color.Blue,
                textDecoration = TextDecoration.Underline
            ),
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


private val defaultFontSize : TextUnit = 16.sp

