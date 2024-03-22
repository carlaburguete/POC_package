package com.ui.components

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ui.components.presentation.HiperlinksCustomExample
import com.ui.components.presentation.MyAlertView
import com.ui.components.presentation.TabViewCustomExample
import com.ui.components.ui.theme.ComponentsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComponentsTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navigationController = rememberNavController()
                    NavHost(navController = navigationController, startDestination = "main") {
                        composable("main") {
                            ButtonNavegation(navigationController)
                        }
                        composable("tabview") {
                            TabViewCustomExample()
                        }
                        composable("alertview") {
                            MyAlertView()
                        }
                        composable("Hiperlink") {
                            HiperlinksCustomExample()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ButtonNavegation(navigationController: NavHostController) {
    Column {
        Button(onClick = { navigationController.navigate("tabview") }) {
            Text(text = "TabView")
        }
        Button(onClick = { navigationController.navigate("alertview") }) {
            Text(text = "AlertView")
        }
        Button(onClick = { navigationController.navigate("Hiperlink") }) {
            Text(text = "Hiperlink")
        }
    }
}









