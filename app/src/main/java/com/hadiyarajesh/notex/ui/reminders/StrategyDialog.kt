package com.hadiyarajesh.notex.ui.reminders

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.hadiyarajesh.notex.database.model.RepetitionStrategy

@Composable
fun StrategyDialog(onStrategyclick: (RepetitionStrategy) -> Unit) {
    MaterialTheme {
        Column {
            val openDialog = remember { mutableStateOf(false) }

            Button(onClick = {
                openDialog.value = true
            }) {
                Text("Set Reminder")
            }

            if (openDialog.value) {

                AlertDialog(
                    onDismissRequest = {
                        // Dismiss the dialog when the user clicks outside the dialog or on the back
                        // button. If you want to disable that functionality, simply use an empty
                        // onCloseRequest.
                        openDialog.value = false
                    },
                    title = {
                        Text(text = "Select Reminder Strategy")
                    },

                    confirmButton = {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Button(

                                onClick = {
                                    onStrategyclick((RepetitionStrategy.None))
                                    openDialog.value = false
                                }) {
                                Text("None")
                            }

                            Button(

                                onClick = {
                                    onStrategyclick((RepetitionStrategy.Daily))

                                    openDialog.value = false
                                }) {
                                Text("Daily")
                            }
                            Button(

                                onClick = {
                                    onStrategyclick((RepetitionStrategy.Weekly))

                                    openDialog.value = false
                                }) {
                                Text("Weekly")
                            }
                            Button(

                                onClick = {
                                    onStrategyclick((RepetitionStrategy.Monthly))
                                    openDialog.value = false
                                }) {
                                Text("Monthly")
                            }
                            Button(

                                onClick = {
                                    onStrategyclick((RepetitionStrategy.Yearly))
                                    openDialog.value = false
                                }) {
                                Text("Yearly")
                            }
                        }


                    }
                )
            }
        }

    }
}