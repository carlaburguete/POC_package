package com.ui.components.components.statusViewCustom

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ui.components.components.config.TextStyleConfig


data class IconStyleConfig(
    val modifierIcon: Modifier = Modifier,
    val tint: Color = Color.Unspecified,
    val selectedTint: Color = Color.Unspecified,
    val unselectedTint: Color = Color.Unspecified,
    val horizontalArrangement: Arrangement.Horizontal = Arrangement.Center
)


data class CustomAlertDialogColors(
    val contentColor: Color,
    val backgroundColor: Color,
    val iconColor: Color,
    val titleColor: Color,
    val messageColor: Color
)


object CustomAlertDialogDefaults {
    /**
     * Creates and returns a [CustomAlertDialogColors] instance with default or custom colors for
     * the AlertDialog. This function allows customization of the content, background, icon, title,
     * and message colors while providing sensible defaults aligned with the Material Theme.
     *
     * @param contentColor The color of the content text in the AlertDialog. Defaults to MaterialTheme's onSurface color.
     * @param backgroundColor The background color of the AlertDialog. Defaults to Transparent.
     * @param iconColor The color of the icon in the AlertDialog. Defaults to the same color as the content by default.
     * @param titleColor The color of the title text in the AlertDialog. Defaults to the same color as the content by default.
     * @param messageColor The color of the message text in the AlertDialog. Defaults to the same color as the content by default.
     * @return A [CustomAlertDialogColors] instance containing the specified colors for use in a CustomAlertDialog.
     */
    @Composable
    fun colors(
        contentColor: Color = MaterialTheme.colorScheme.onSurface,
        backgroundColor: Color = Color.Transparent,
        iconColor: Color = contentColor,
        titleColor: Color = contentColor,
        messageColor: Color = contentColor
    ) = CustomAlertDialogColors(contentColor, backgroundColor, iconColor, titleColor, messageColor)
}

data class CardStyleConfig(
    val modifier: Modifier = Modifier,
    val shape: Shape = RoundedCornerShape(0.dp),
    val backgroundColor: Color = Color.Unspecified,
    val contentColor: Color = Color.Unspecified,
    val elevation: Dp = 0.dp,
    val border: BorderStroke? = null
)


data class DialogButtonConfig(
    val label: String,
    val containerColor: Color = Color.Unspecified,
    val contentColor: Color = Color.Unspecified,
    val disabledContainerColor: Color = Color.Unspecified,
    val disabledContentColor: Color = Color.Unspecified,
    val modifier: Modifier = Modifier,
    val typeButtonStyle: TypeButtonStyle
)

data class AlertDialogContent(
    val title: String? = null,
    val message: String,
    val icon: ImageVector? = null,
    val buttons: List<DialogButtonConfig> = emptyList()
)

enum class ButtonLayoutStyle {
    SideBySide,
    Stacked,
}

enum class TypeButtonStyle {
    Button,
    Text,
}

/**
 * A composable function that displays a customizable AlertDialog. This dialog supports customization
 * for its title, message, buttons, and icon with optional styling for each component. Additionally,
 * it allows configuring the behavior upon dismissal and provides flexibility in the arrangement and
 * styling of action buttons.
 *
 * @param content The content of the AlertDialog, including title, message, icon, and buttons.
 * @param onDismissRequest Optional callback executed when the dialog is dismissed by the user. This callback
 * is only invoked if onDismissEnable is true.
 * @param onDismissEnable A flag determining whether the onDismissRequest callback should be invoked. If false,
 * the dialog will not dismiss automatically on outside touches or back press, and the onDismissRequest
 * callback will not be called.
 * @param colors Custom colors for various parts of the AlertDialog, allowing for comprehensive theming.
 * @param titleTextStyle Configuration for styling the title text, providing flexibility in font size,
 * weight, and color.
 * @param messageTextStyle Configuration for styling the message text. This allows for customization
 * of the dialog's main content appearance.
 * @param buttonTextStyle Configuration for styling the text of the buttons, enabling differentiation
 * between button types or actions through text styling.
 * @param iconStyleConfig Configuration for styling the icon, offering options to adjust its size,
 * tint, and placement within the dialog.
 * @param buttonLayoutStyle Determines the layout of the buttons (side by side or stacked), catering
 * to different design needs or preferences.
 * @param cardStyleConfig Configuration for styling the card that contains the AlertDialog content,
 * allowing for adjustments in elevation, shape, and other card attributes.
 * @param onFirstActionButton Optional callback executed when the first action button is clicked. This provides
 * a mechanism for handling primary actions.
 * @param onSecondActionButton Optional callback executed when the second action button is clicked. This allows
 * for secondary actions or alternative choices to be handled.
 *
 * This AlertDialog variant offers extensive customization for various components, making it suitable
 * for a wide range of use cases where the default styling and behavior need to be adjusted.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAlertDialog(
    content: AlertDialogContent,
    onDismissRequest: (() -> Unit)? = null,
    onDismissEnable: Boolean,
    colors: CustomAlertDialogColors = CustomAlertDialogDefaults.colors(),
    titleTextStyle: TextStyleConfig = TextStyleConfig(),
    messageTextStyle: TextStyleConfig = TextStyleConfig(),
    buttonTextStyle: TextStyleConfig = TextStyleConfig(),
    iconStyleConfig: IconStyleConfig = IconStyleConfig(),
    buttonLayoutStyle: ButtonLayoutStyle = ButtonLayoutStyle.Stacked,
    cardStyleConfig: CardStyleConfig = CardStyleConfig(),
    onFirstActionButton: (() -> Unit)? = null,
    onSecondActionButton: (() -> Unit)? = null,
) {
    val buttonActions = mutableListOf<() -> Unit>()
    onFirstActionButton?.let { buttonActions.add(it) }
    onSecondActionButton?.let { buttonActions.add(it) }

    AlertDialog(
        onDismissRequest = {
            onDismissEnable.let {
                onDismissRequest
            } ?: run {
                null
            }
        },
        content = {
            CustomCard(cardStyleConfig = cardStyleConfig, colors) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    content.icon?.let { tabItem ->
                        CustomTabIcon(
                            tabItem,
                            iconStyleConfig = iconStyleConfig,
                            colors
                        )
                    }
                    content.title?.let {
                        TabText(
                            text = it,
                            color = colors.titleColor,
                            textStyleConfig = titleTextStyle,
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    TabText(
                        text = content.message,
                        color = colors.messageColor,
                        textStyleConfig = messageTextStyle,
                    )
                    LayoutButtons(
                        buttons = content.buttons,
                        style = buttonLayoutStyle,
                        buttonTextStyle,
                        buttonActions,
                    )
                }
            }

        },
    )
}

/**
 * A composable function that lays out buttons in an AlertDialog. This function supports two layout styles:
 * side by side or stacked. It also allows customization of the button's appearance and behavior.
 *
 * @param buttons A list of [DialogButtonConfig] defining each button's content and actions.
 * @param style The layout style for the buttons, either [ButtonLayoutStyle.SideBySide] or [ButtonLayoutStyle.Stacked].
 * @param buttonTextStyle Configuration for styling the text of the buttons, providing options for font size,
 * weight, color, and other text attributes.
 * @param buttonActions A list of callbacks corresponding to the actions associated with each button. The order of
 * callbacks should match the order of buttons in the 'buttons' parameter.
 */
@Composable
fun LayoutButtons(
    buttons: List<DialogButtonConfig>,
    style: ButtonLayoutStyle,
    buttonTextStyle: TextStyleConfig,
    buttonActions: MutableList<() -> Unit>
) {
    when (style) {
        ButtonLayoutStyle.SideBySide -> Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            buttons.forEachIndexed { index, button ->
                val action = buttonActions.getOrNull(index) ?: return@forEachIndexed

                when (button.typeButtonStyle) {
                    TypeButtonStyle.Button -> {
                        val containerColor =
                            if (button.containerColor == Color.Unspecified) MaterialTheme.colorScheme.primary else button.containerColor

                        val contentColor =
                            if (button.contentColor == Color.Unspecified) MaterialTheme.colorScheme.onPrimary else button.contentColor

                        val disabledContainerColor =
                            if (button.disabledContainerColor == Color.Unspecified) MaterialTheme.colorScheme.primary.copy(
                                alpha = 0.38f
                            ) else button.disabledContainerColor

                        val disabledContentColor =
                            if (button.disabledContentColor == Color.Unspecified) MaterialTheme.colorScheme.onPrimary.copy(
                                alpha = 0.38f
                            ) else button.disabledContentColor

                        Button(
                            onClick = action,
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = containerColor,
                                contentColor = contentColor,
                                disabledContainerColor = disabledContainerColor,
                                disabledContentColor = disabledContentColor
                            ),
                        ) {
                            TabText(button.label, contentColor, buttonTextStyle, action)
                        }
                    }

                    TypeButtonStyle.Text -> {
                        val contentColor =
                            if (button.contentColor == Color.Unspecified) MaterialTheme.colorScheme.onPrimary else button.contentColor
                        Box(modifier = Modifier.fillMaxHeight().fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                            TabText(button.label, contentColor, buttonTextStyle, action)

                        }

                    }
                }
            }
        }

        ButtonLayoutStyle.Stacked -> Column {
            buttons.forEachIndexed { index, button ->
                val action = buttonActions.getOrNull(index) ?: return@forEachIndexed

                when (button.typeButtonStyle) {
                    TypeButtonStyle.Button -> {
                        val containerColor =
                            if (button.containerColor == Color.Unspecified) MaterialTheme.colorScheme.primary else button.containerColor

                        val contentColor =
                            if (button.contentColor == Color.Unspecified) MaterialTheme.colorScheme.onPrimary else button.contentColor

                        val disabledContainerColor =
                            if (button.disabledContainerColor == Color.Unspecified) MaterialTheme.colorScheme.primary.copy(
                                alpha = 0.38f
                            ) else button.disabledContainerColor

                        val disabledContentColor =
                            if (button.disabledContentColor == Color.Unspecified) MaterialTheme.colorScheme.onPrimary.copy(
                                alpha = 0.38f
                            ) else button.disabledContentColor

                        Button(
                            onClick = action,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = containerColor,
                                contentColor = contentColor,
                                disabledContainerColor = disabledContainerColor,
                                disabledContentColor = disabledContentColor
                            ),
                            modifier = button.modifier.fillMaxWidth()
                        ) {
                            TabText(button.label, contentColor, buttonTextStyle, action)
                        }
                    }

                    TypeButtonStyle.Text -> {
                        val contentColor =
                            if (button.contentColor == Color.Unspecified) MaterialTheme.colorScheme.onPrimary else button.contentColor

                        TabText(button.label, contentColor, buttonTextStyle, action)
                    }
                }
            }
        }
    }
}


/**
 * A composable function that creates a customizable card component for use within an AlertDialog.
 * This card supports styling for its shape, color, border, and content alignment.
 *
 * @param cardStyleConfig Configuration for styling the card's appearance.
 * @param dialogColors Custom colors applied to the card, influencing its background and content color.
 * @param content A composable lambda defining the content to be displayed within the card.
 */
@Composable
fun CustomCard(
    cardStyleConfig: CardStyleConfig = CardStyleConfig(),
    dialogColors: CustomAlertDialogColors,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = cardStyleConfig.modifier,
        shape = cardStyleConfig.shape,
        colors = CardDefaults.cardColors(
            containerColor = dialogColors.backgroundColor,
            contentColor = dialogColors.contentColor
        ),
        border = cardStyleConfig.border,
        content = content
    )
}

/**
 * A composable function that displays text within the AlertDialog. This function supports comprehensive
 * text styling, including font size, weight, style, family, and alignment. It also supports an optional
 * click action.
 *
 * @param text The text to be displayed.
 * @param color The color of the text.
 * @param textStyleConfig Configuration for styling the text's appearance.
 * @param onClick An optional lambda executed when the text is clicked.
 */
@Composable
fun TabText(
    text: String,
    color: Color,
    textStyleConfig: TextStyleConfig,
    onClick: (() -> Unit)? = null,
) {
    Text(
        text = text,
        color = color,
        fontSize = textStyleConfig.fontSize,
        fontStyle = textStyleConfig.fontStyle,
        fontWeight = textStyleConfig.fontWeight,
        fontFamily = textStyleConfig.fontFamily,
        letterSpacing = textStyleConfig.letterSpacing,
        textDecoration = textStyleConfig.textDecoration,
        overflow = textStyleConfig.overflow,
        softWrap = textStyleConfig.softWrap,
        maxLines = textStyleConfig.maxLines,
        minLines = textStyleConfig.minLines,
        modifier = textStyleConfig.modifier.clickable {
            onClick?.invoke()
        },
        textAlign = textStyleConfig.textAlign
    )
}

/**
 * A composable function that displays an icon within the AlertDialog. This function allows for customization
 * of the icon's size, tint, and alignment within its container.
 *
 * @param tabItem The [ImageVector] to be used as the icon.
 * @param iconStyleConfig Configuration for styling the icon's appearance and placement.
 * @param colors Custom colors influencing the icon's tint.
 */
@Composable
fun CustomTabIcon(
    tabItem: ImageVector,
    iconStyleConfig: IconStyleConfig,
    colors: CustomAlertDialogColors
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = iconStyleConfig.horizontalArrangement
    ) {
        Box {
            Icon(
                imageVector = tabItem,
                contentDescription = null,
                modifier = iconStyleConfig.modifierIcon,
                tint = colors.iconColor
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun ShowStyledCustomAlertDialogText() {
    CustomAlertDialog(
        cardStyleConfig = CardStyleConfig(shape = RoundedCornerShape(16.dp)),
        onDismissEnable = true,
        content = AlertDialogContent(
            title = "Alerta Importante",
            message = "Este mensaje es una alerta crítica para informar sobre un evento importante. Por favor, toma las medidas necesarias.",
            icon = Icons.Filled.Warning,
            buttons = listOf(
                DialogButtonConfig(
                    label = "Aceptar",
                    containerColor = Color.Black,
                    contentColor = Color.Black,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.DarkGray,
                    modifier = Modifier.padding(8.dp),
                    typeButtonStyle = TypeButtonStyle.Text
                ),
                DialogButtonConfig(
                    label = "Cancelar",
                    containerColor = Color.Black,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.DarkGray,
                    modifier = Modifier.padding(8.dp),
                    typeButtonStyle = TypeButtonStyle.Button
                )

            )
        ),
        onFirstActionButton = {},
        onSecondActionButton = {},
        onDismissRequest = {},
        colors = CustomAlertDialogDefaults.colors(
            contentColor = Color.Black,
            backgroundColor = Color.White,
            iconColor = Color.Black,
            titleColor = Color.Black,
            messageColor = Color.Black
        ),
        titleTextStyle = TextStyleConfig(
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        ),
        messageTextStyle = TextStyleConfig(
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        ),
        iconStyleConfig = IconStyleConfig(
            modifierIcon = Modifier.size(48.dp),
            tint = Color.Black,
        ),
        buttonLayoutStyle = ButtonLayoutStyle.Stacked,
        buttonTextStyle = TextStyleConfig(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    )
}


@Preview(showBackground = true)
@Composable
fun ShowStyledCustomAlertDialogSlideBySlideText() {
    CustomAlertDialog(
        content = AlertDialogContent(
            title = "Alerta Importante",
            message = "Este mensaje es una alerta crítica para informar sobre un evento importante. Por favor, toma las medidas necesarias.",
            icon = Icons.Filled.Warning,
            buttons = listOf(
                DialogButtonConfig(
                    label = "Cancelar",
                    containerColor = Color.Black,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.DarkGray,
                    typeButtonStyle = TypeButtonStyle.Button
                ),
                DialogButtonConfig(
                    label = "Aceptar",
                    containerColor = Color.Black,
                    contentColor = Color.Black,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.DarkGray,
                    typeButtonStyle = TypeButtonStyle.Text
                )
            )
        ),
        onDismissRequest = {},
        onFirstActionButton = {},
        onSecondActionButton = {},
        colors = CustomAlertDialogDefaults.colors(
            contentColor = Color.Black,
            backgroundColor = Color.White,
            iconColor = Color.Black,
            titleColor = Color.Black,
            messageColor = Color.Black
        ),
        titleTextStyle = TextStyleConfig(
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        ),
        messageTextStyle = TextStyleConfig(
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        ),
        iconStyleConfig = IconStyleConfig(
            modifierIcon = Modifier.size(48.dp),
            tint = Color.Black,
        ),
        buttonLayoutStyle = ButtonLayoutStyle.SideBySide,
        onDismissEnable = true,
        buttonTextStyle = TextStyleConfig(
            textDecoration = TextDecoration.Underline
        )
    )
}

@Preview(showBackground = true)
@Composable
fun ShowStyledCustomAlertDialogButton() {
    CustomAlertDialog(
        cardStyleConfig = CardStyleConfig(shape = RoundedCornerShape(16.dp)),
        onDismissEnable = true,
        content = AlertDialogContent(
            title = "Alerta Importante",
            message = "Este mensaje es una alerta crítica para informar sobre un evento importante. Por favor, toma las medidas necesarias.",
            icon = Icons.Filled.Warning,
            buttons = listOf(
                DialogButtonConfig(
                    label = "Aceptar",
                    containerColor = Color.Black,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.DarkGray,
                    modifier = Modifier.padding(8.dp),
                    typeButtonStyle = TypeButtonStyle.Button
                ),
                DialogButtonConfig(
                    label = "Cancelar",
                    containerColor = Color.Black,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.DarkGray,
                    modifier = Modifier.padding(8.dp),
                    typeButtonStyle = TypeButtonStyle.Button
                )
            )
        ),
        onDismissRequest = {},
        onFirstActionButton = {},
        colors = CustomAlertDialogDefaults.colors(
            contentColor = Color.Black,
            backgroundColor = Color.White,
            iconColor = Color.Black,
            titleColor = Color.Black,
            messageColor = Color.Black
        ),
        titleTextStyle = TextStyleConfig(
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        ),
        messageTextStyle = TextStyleConfig(
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        ),
        iconStyleConfig = IconStyleConfig(
            modifierIcon = Modifier.size(48.dp),
            tint = Color.Black,
        ),
        buttonLayoutStyle = ButtonLayoutStyle.Stacked,
        buttonTextStyle = TextStyleConfig(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    )
}


@Preview(showBackground = true)
@Composable
fun ShowStyledCustomAlertDialogSlideBySlideButton() {
    CustomAlertDialog(
        content = AlertDialogContent(
            title = "Alerta Importante",
            message = "Este mensaje es una alerta crítica para informar sobre un evento importante. Por favor, toma las medidas necesarias.",
            icon = Icons.Filled.Warning,
            buttons = listOf(
                DialogButtonConfig(
                    label = "Cancelar",
                    containerColor = Color.Black,
                    contentColor = Color.White,
                    typeButtonStyle = TypeButtonStyle.Button
                ),
                DialogButtonConfig(
                    label = "Aceptar",
                    containerColor = Color.Black,
                    contentColor = Color.White,
                    typeButtonStyle = TypeButtonStyle.Button
                )
            )
        ),
        onDismissRequest = {},
        onFirstActionButton = {},
        colors = CustomAlertDialogDefaults.colors(
            contentColor = Color.Black,
            backgroundColor = Color.White,
            iconColor = Color.Black,
            titleColor = Color.Black,
            messageColor = Color.Black
        ),
        titleTextStyle = TextStyleConfig(
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        ),
        messageTextStyle = TextStyleConfig(
            fontSize = 16.sp,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth()
        ),
        iconStyleConfig = IconStyleConfig(
            modifierIcon = Modifier.size(48.dp),
            tint = Color.Black,
            horizontalArrangement = Arrangement.Center
        ),
        buttonLayoutStyle = ButtonLayoutStyle.SideBySide,
        onDismissEnable = true,
    )
}


