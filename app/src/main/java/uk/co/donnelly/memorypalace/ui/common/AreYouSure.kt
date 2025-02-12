package uk.co.donnelly.memorypalace.ui.common

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
fun AreYouSure(
    titleText: String,
    bodyText: String,
    negativeButtonText: String,
    positiveButtonText: String,
    action: () -> Unit,
    displayState: MutableState<Boolean>
) {
    AlertDialog(
        onDismissRequest = { displayState.value = false },
        title = {
            Text(titleText)
        },
        text = {
            Text(bodyText)
        },
        confirmButton = {
            Button(
                onClick = action
            ) {
                Text(positiveButtonText)
            }
        },
        dismissButton = {
            Button(onClick = {displayState.value = false}) {
                Text(negativeButtonText)
            }
        })
}