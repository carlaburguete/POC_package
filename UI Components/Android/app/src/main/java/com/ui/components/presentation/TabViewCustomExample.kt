package com.ui.components.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.ui.components.components.tabviewcustom.BadgeStyleConfig
import com.ui.components.components.tabviewcustom.CustomTabItem
import com.ui.components.components.tabviewcustom.CustomTabView
import com.ui.components.components.tabviewcustom.IconStyleConfig


@Preview(showBackground = true)
@Composable
fun TabViewCustomExample() {
    Column {
        var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }
        val num = rememberSaveable { mutableIntStateOf(0) }

        val tabs = listOf(
            CustomTabItem(
                icon = { Icon(Icons.Filled.Home, contentDescription = "Profile") },
                text = "Home",
                badgeNumber = num.intValue,
                action = { selectedTabIndex = 0 }
            ),

            CustomTabItem(
                icon = { Icon(Icons.Filled.Favorite, contentDescription = "Profile") },
                text = "Favorites",
                badgeNumber = 1,
                action = { selectedTabIndex = 1 }
            ),
            CustomTabItem(
                icon = { Icon(Icons.Filled.Person, contentDescription = "Profile") },
                text = "Profile",
                badgeNumber = 0,
                action = { selectedTabIndex = 2 }
            )
        )

        CustomTabView(
            tabs = tabs,
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
                softWrap = true
            ),
            iconStyleConfig = IconStyleConfig(
                unselectedTint = Color.Red,
                selectedTint = Color.Black,
                tint = Color.White,
                modifier = Modifier.padding(1.dp)
            ),
            badgeStyleConfig = BadgeStyleConfig(
                textColor = Color.Blue,
                backgroundColor = Color.Green
            )
        )

        when (selectedTabIndex) {
            0 -> NumberList(num)
            1 -> LetterList()
            2 -> {}
        }

    }


}

@Composable
fun NumberList(num: MutableState<Int>) {
    Column {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items((1..10).toList()) { number ->
                Text(text = number.toString(), modifier = Modifier.fillMaxWidth())
                Divider()
            }
        }
        Button(onClick = { num.value++ }) {

        }

    }

}

@Composable
fun LetterList() {
    val letters = ('A'..'Z').toList()
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(letters) { letter ->
            Text(text = letter.toString(), modifier = Modifier.fillMaxWidth())
            Divider()
        }
    }
}