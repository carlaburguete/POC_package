package com.ui.components.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ui.components.components.config.TextStyleConfig
import com.ui.components.components.statusViewCustom.AlertDialogContent
import com.ui.components.components.statusViewCustom.ButtonLayoutStyle
import com.ui.components.components.statusViewCustom.CardStyleConfig
import com.ui.components.components.statusViewCustom.CustomAlertDialog
import com.ui.components.components.statusViewCustom.CustomAlertDialogDefaults
import com.ui.components.components.statusViewCustom.DialogButtonConfig
import com.ui.components.components.statusViewCustom.IconStyleConfig
import com.ui.components.components.statusViewCustom.TypeButtonStyle


@Composable
fun MyAlertView() {
    var showDialog by remember { mutableStateOf(true) }
    if (showDialog) {
        ShowStyledCustomAlertDialog(onDialogDismiss = { showDialog = false })
    }
}


@Composable
fun ShowStyledCustomAlertDialog(onDialogDismiss: () -> Unit) {
    CustomAlertDialog(
        cardStyleConfig = CardStyleConfig(shape = RoundedCornerShape(16.dp)),
        content = AlertDialogContent(
            title = "Alerta Importante",
            message = "Este mensaje es una alerta crítica para informar sobre un evento importante. Por favor, toma las medidas necesarias.",
            icon = Icons.Filled.Warning,
            buttons = listOf(
                DialogButtonConfig(
                    label = "Aceptar",
                    contentColor = Color.Black,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.DarkGray,
                    modifier = Modifier.padding(8.dp),
                    typeButtonStyle = TypeButtonStyle.Text
                )
            )
        ),
        onFirstActionButton = onDialogDismiss,
        onDismissRequest = {onDialogDismiss()},
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
        onDismissEnable = false,
        buttonTextStyle = TextStyleConfig(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    )
}