package com.rajan.ecommerce.presentation.ui_components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun AppMessageDialog(
    show: Boolean,
    title: String,
    message: String,
    onDismiss: () -> Unit
) {
    if (show) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            confirmButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text(text = "Okay")
                }
            },
            title = { Text(text = title) },
            text = { Text(text = message) },

            )
    }
}