package com.example.coursework.presentation

import androidx.compose.material3.*
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