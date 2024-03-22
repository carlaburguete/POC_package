package com.ui.components.components.tabviewcustom

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgeDefaults
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ui.components.components.config.TextStyleConfig

data class CustomTabItem(
    val icon: @Composable (() -> Unit)?,
    val text: String,
    val badgeNumber: Int,
    val action: () -> Unit,
)

data class IconStyleConfig(
    val modifier: Modifier = Modifier,
    val tint: Color = Color.Unspecified,
    val selectedTint: Color = Color.Unspecified,
    val unselectedTint: Color = Color.Unspecified
)

data class BadgeStyleConfig(
    val backgroundColor: Color = Color.Unspecified,
    val textColor: Color = Color.Unspecified,
    val modifier: Modifier = Modifier,
)

data class CustomTabViewColors(
    val selectedColor: Color,
    val unselectedColor: Color,
    val contentColor: Color,
    val backgroundColor: Color
)

object CustomTabViewDefaults {
    /**
     * Creates a [CustomTabViewColors] that will be used as default colors for the CustomTabView,
     * including a default transparent background color. This configuration allows for a consistent
     * appearance across different states of the tabs.
     *
     * @param selectedColor the color to use for the tab when selected.
     * @param unselectedColor the color to use for the tab when unselected.
     * @param contentColor the color to use for the content within the tabs, including icons and text.
     * @param backgroundColor the background color of the tab row, defaults to transparent.
     * @return the resulting [CustomTabViewColors] used for the CustomTabView.
     */
    @Composable
    fun colors(
        selectedColor: Color = MaterialTheme.colorScheme.primary,
        unselectedColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
        contentColor: Color = MaterialTheme.colorScheme.onSurface,
        backgroundColor: Color = Color.Transparent
    ) = CustomTabViewColors(selectedColor, unselectedColor, contentColor, backgroundColor)
}


/**
 * A composable function that creates a customizable tab view with optional styling for text,
 * icons, and badges. It supports both selected and unselected states with customizable colors.
 *
 * @param selectedTabIndex the index of the initially selected tab.
 * @param tabs a list of [CustomTabItem] defining each tab's content.
 * @param colors [CustomTabViewColors] instance providing colors for selected, unselected tabs,
 *        content, and background.
 * @param onTabSelected a callback function invoked with the new selected tab index.
 * @param textStyleConfig configuration for styling the text in each tab.
 * @param iconStyleConfig configuration for styling the icons in each tab.
 * @param badgeStyleConfig configuration for styling the badges in each tab.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTabView(
    selectedTabIndex: Int = 0,
    tabs: List<CustomTabItem>,
    colors: CustomTabViewColors = CustomTabViewDefaults.colors(),
    onTabSelected: (position: Int) -> Unit,
    textStyleConfig: TextStyleConfig = TextStyleConfig(),
    iconStyleConfig: IconStyleConfig = IconStyleConfig(),
    badgeStyleConfig: BadgeStyleConfig = BadgeStyleConfig(),
) {
    val currentSelectedIndex = rememberSaveable { mutableIntStateOf(selectedTabIndex) }
    val hasBadgeGreaterThanZero = badgeNumberGreaterThanZero(tabs)

    TabRow(
        modifier = Modifier.wrapContentHeight(),
        selectedTabIndex = currentSelectedIndex.intValue,
        containerColor = colors.backgroundColor,
        contentColor = colors.contentColor,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.tabIndicatorOffset(tabPositions[currentSelectedIndex.intValue])
            )
        }
    ) {
        tabs.forEachIndexed { index, tabItem ->
            if (tabItem.text.isEmpty()) {
                if (hasBadgeGreaterThanZero) {

                    Tab(
                        selected = currentSelectedIndex.intValue == index,
                        onClick = {
                            currentSelectedIndex.intValue = index
                            onTabSelected(index)
                            tabItem.action()
                        },
                        icon = {
                            BadgedBox(
                                modifier = Modifier.padding(top = 4.dp),
                                badge = {
                                    CustomBadge(
                                        badgeNumber = tabItem.badgeNumber,
                                        badgeStyleConfig = badgeStyleConfig
                                    )
                                }
                            ) {
                                CustomTabIcon(
                                    iconComposable = { tabItem.icon?.invoke() },
                                    isSelected = currentSelectedIndex.intValue == index,
                                    iconStyleConfig = iconStyleConfig
                                )
                            }
                        },
                    )

                } else {
                    Tab(
                        selected = currentSelectedIndex.intValue == index,
                        onClick = {
                            currentSelectedIndex.intValue = index
                            onTabSelected(index)
                            tabItem.action()
                        },
                        icon = {
                            CustomTabIcon(
                                iconComposable = { tabItem.icon?.invoke() },
                                isSelected = currentSelectedIndex.intValue == index,
                                iconStyleConfig = iconStyleConfig
                            )
                        },
                    )
                }

            } else {
                tabItem.icon?.let {
                    if (hasBadgeGreaterThanZero) {
                        Tab(
                            selected = currentSelectedIndex.intValue == index,
                            onClick = {
                                currentSelectedIndex.intValue = index
                                onTabSelected(index)
                                tabItem.action()
                            },
                            text = {
                                Row {
                                    TabText(
                                        text = tabItem.text,
                                        isSelected = currentSelectedIndex.intValue == index,
                                        selectedColor = colors.selectedColor,
                                        unselectedColor = colors.unselectedColor,
                                        textStyleConfig = textStyleConfig
                                    )
                                }
                            },
                            icon = {
                                BadgedBox(
                                    modifier = Modifier.padding(top = 12.dp),
                                    badge = {
                                        CustomBadge(
                                            badgeNumber = tabItem.badgeNumber,
                                            badgeStyleConfig = badgeStyleConfig
                                        )
                                    }
                                ) {
                                    CustomTabIcon(
                                        iconComposable = { it.invoke() },
                                        isSelected = currentSelectedIndex.intValue == index,
                                        iconStyleConfig = iconStyleConfig
                                    )

                                }
                            },
                        )
                    } else {
                        Tab(
                            selected = currentSelectedIndex.intValue == index,
                            onClick = {
                                currentSelectedIndex.intValue = index
                                onTabSelected(index)
                                tabItem.action()
                            },
                            text = {
                                Row {
                                    TabText(
                                        text = tabItem.text,
                                        isSelected = currentSelectedIndex.intValue == index,
                                        selectedColor = colors.selectedColor,
                                        unselectedColor = colors.unselectedColor,
                                        textStyleConfig = textStyleConfig
                                    )
                                }
                            },
                            icon = {
                                CustomTabIcon(
                                    iconComposable = { it.invoke() },
                                    isSelected = currentSelectedIndex.intValue == index,
                                    iconStyleConfig = iconStyleConfig
                                )
                            },
                        )
                    }
                } ?: run {
                    Tab(
                        modifier = Modifier.wrapContentHeight(),
                        selected = currentSelectedIndex.intValue == index,
                        onClick = {
                            currentSelectedIndex.intValue = index
                            onTabSelected(index)
                            tabItem.action()
                        },
                        text = {
                            Row {
                                TabText(
                                    text = tabItem.text,
                                    isSelected = currentSelectedIndex.intValue == index,
                                    selectedColor = colors.selectedColor,
                                    unselectedColor = colors.unselectedColor,
                                    textStyleConfig = textStyleConfig
                                )
                                BadgedBox(
                                    badge = {
                                        CustomBadge(
                                            badgeNumber = tabItem.badgeNumber,
                                            badgeStyleConfig = badgeStyleConfig
                                        )
                                    }
                                ) {}

                            }
                        },
                    )
                }

            }

        }
    }
}

fun badgeNumberGreaterThanZero(tabs: List<CustomTabItem>): Boolean {
    return tabs.any { it.badgeNumber > 0 }
}


/**
 * Displays text within a tab, changing color based on whether the tab is selected.
 *
 * @param text the text to display.
 * @param isSelected whether the tab is selected.
 * @param selectedColor color for text when the tab is selected.
 * @param unselectedColor color for text when the tab is unselected.
 * @param textStyleConfig configuration for text styling.
 */
@Composable
fun TabText(
    text: String,
    isSelected: Boolean,
    selectedColor: Color,
    unselectedColor: Color,
    textStyleConfig: TextStyleConfig
) {
    Text(
        text = text,
        color = if (isSelected) selectedColor else unselectedColor,
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
        modifier = textStyleConfig.modifier,
        textAlign = textStyleConfig.textAlign
    )
}


/**
 * Displays an icon within a tab, applying tint based on whether the tab is selected.
 *
 * @param iconComposable the composable that generates the icon.
 * @param isSelected whether the tab is selected.
 * @param iconStyleConfig configuration for icon styling, including tint.
 */
@Composable
fun CustomTabIcon(
    iconComposable: @Composable () -> Unit,
    isSelected: Boolean,
    iconStyleConfig: IconStyleConfig
) {
    val tint = when {
        isSelected && iconStyleConfig.selectedTint != Color.Unspecified -> iconStyleConfig.selectedTint
        !isSelected && iconStyleConfig.unselectedTint != Color.Unspecified -> iconStyleConfig.unselectedTint
        else -> iconStyleConfig.tint
    }
    Box(modifier = iconStyleConfig.modifier) {
        CompositionLocalProvider(LocalContentColor provides tint) {
            iconComposable()
        }
    }
}


/**
 * Displays a badge within a tab, often used to indicate a count or notification.
 *
 * @param badgeNumber the number to display within the badge.
 * @param badgeStyleConfig configuration for badge styling, including background and text color.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomBadge(
    badgeNumber: Int,
    badgeStyleConfig: BadgeStyleConfig
) {
    if (badgeNumber > 0) {
        Badge(modifier = badgeStyleConfig.modifier,
            containerColor = badgeStyleConfig.backgroundColor.takeUnless { it == Color.Unspecified }
                ?: BadgeDefaults.containerColor,
            contentColor = badgeStyleConfig.textColor.takeUnless { it == Color.Unspecified }
                ?: contentColorFor(BadgeDefaults.containerColor)
        ) {
            Text(
                text = badgeNumber.toString(),
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MyTabViewIconsText() {
    Column {

        val tabs = listOf(
            CustomTabItem(
                icon = { Icon(Icons.Filled.Person, contentDescription = "Profile") },
                text = "UNO",
                badgeNumber = 0,
                action = { }
            ),
            CustomTabItem(
                icon = { Icon(Icons.Filled.Person, contentDescription = "Profile") },
                text = "DOS",
                badgeNumber = 1,
                action = { }
            ),
            CustomTabItem(
                icon = { Icon(Icons.Filled.Person, contentDescription = "Profile") },
                text = "TRES",
                badgeNumber = 0,
                action = { }
            )
        )

        CustomTabView(
            tabs = tabs,
            colors = CustomTabViewColors(
                backgroundColor = Color.Red,
                contentColor = Color.White,
                selectedColor = Color.Black,
                unselectedColor = Color.LightGray
            ),
            onTabSelected = { position ->
                println("Tab seleccionado en la posición: $position")
            },
            badgeStyleConfig = BadgeStyleConfig(
                backgroundColor = Color.White,
                textColor = Color.Red
            )

        )

    }
}

@Preview(showBackground = true)
@Composable
fun MyTabViewText() {

    val tabsText = listOf(
        CustomTabItem(
            icon = null,
            text = "Home",
            badgeNumber = 2,
            action = { }
        ),
        CustomTabItem(
            icon = null,
            text = "Favorites",
            badgeNumber = 1,
            action = { }
        ),
        CustomTabItem(
            icon = null,
            text = "Profile",
            badgeNumber = 0,
            action = { }
        )
    )

    CustomTabView(
        tabs = tabsText,
        colors = CustomTabViewColors(
            backgroundColor = Color.Red,
            contentColor = Color.White,
            selectedColor = Color.Black,
            unselectedColor = Color.LightGray
        ),
        onTabSelected = { position ->
            println("Tab seleccionado en la posición: $position")
        },
        badgeStyleConfig = BadgeStyleConfig(
            textColor = Color.Blue,
            backgroundColor = Color.Green,
        )
    )

}

@Preview(showBackground = true)
@Composable
fun MyTabViewIcons() {
    Column {

        val tabs = listOf(
            CustomTabItem(
                icon = { Icon(Icons.Filled.Person, contentDescription = "Profile") },
                text = "",
                badgeNumber = 0,
                action = { }
            ),
            CustomTabItem(
                icon = { Icon(Icons.Filled.Person, contentDescription = "Profile") },
                text = "",
                badgeNumber = 1,
                action = { }
            ),
            CustomTabItem(
                icon = { Icon(Icons.Filled.Person, contentDescription = "Profile") },
                text = "",
                badgeNumber = 0,
                action = { }
            )
        )

        CustomTabView(
            tabs = tabs,
            colors = CustomTabViewColors(
                backgroundColor = Color.Red,
                contentColor = Color.White,
                selectedColor = Color.Black,
                unselectedColor = Color.LightGray
            ),
            onTabSelected = { position ->
                println("Tab seleccionado en la posición: $position")
            },
        )

    }
}

@Preview(showBackground = true)
@Composable
fun MyTabExample() {
    Column {

        val tabs = listOf(
            CustomTabItem(
                icon = { Icon(Icons.Filled.Person, contentDescription = "Profile") },
                text = "uno",
                badgeNumber = 2,
                action = { }
            ),
            CustomTabItem(
                icon = { Icon(Icons.Filled.Person, contentDescription = "Profile") },
                text = "dos",
                badgeNumber = 1,
                action = { }
            ),
            CustomTabItem(
                icon = { Icon(Icons.Filled.Person, contentDescription = "Profile") },
                text = "tres",
                badgeNumber = 0,
                action = { }
            )
        )

        CustomTabView(
            tabs = tabs,
            colors = CustomTabViewColors(
                backgroundColor = Color.Red,
                contentColor = Color.White,
                selectedColor = Color.Black,
                unselectedColor = Color.LightGray
            ),
            onTabSelected = { position ->
                println("Tab seleccionado en la posición: $position")
            },
            textStyleConfig = TextStyleConfig(
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                fontFamily = FontFamily.Cursive,
                maxLines = 1,
                textDecoration = TextDecoration.Underline,
                letterSpacing = 2.sp,
                minLines = 1,
                overflow = TextOverflow.Clip,
                softWrap = true,
                modifier = Modifier.padding(0.dp)
            ),
            iconStyleConfig = IconStyleConfig(
                unselectedTint = Color.White,
                selectedTint = Color.Black,
                tint = Color.White,
                modifier = Modifier.padding(1.dp)
            ),
            badgeStyleConfig = BadgeStyleConfig(
                textColor = Color.Blue,
                backgroundColor = Color.Green
            )
        )

    }
}




