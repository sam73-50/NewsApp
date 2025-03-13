package com.newsappwithrssfeed.presentation.screen.news_home_screen

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.newsappwithrssfedd.R

@Composable
fun ErrorDialog(showDialog: Boolean, errorMessage: String, onDismiss: () -> Unit) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text(stringResource(id = R.string.error)) },
            text = { Text(errorMessage) },
            confirmButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text("OK")
                }
            }
        )
    }
}