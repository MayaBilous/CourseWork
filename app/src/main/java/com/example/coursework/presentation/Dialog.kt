package com.example.coursework.presentation

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun ShowDialog(text: String, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = null,
        text = { Text(text) },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("OK".uppercase())
            }
        }
    )
}