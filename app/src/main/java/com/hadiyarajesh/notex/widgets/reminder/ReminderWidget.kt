package com.hadiyarajesh.notex.widgets.reminder


import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.CircularProgressIndicator
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.*
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle

class ReminderWidget : GlanceAppWidget() {

    override val stateDefinition: GlanceStateDefinition<*>
        get() = ReminderDataStateDefinition


    @Composable
    override fun Content() {
        val reminderData = currentState<ReminderData>()
        Column(
            modifier = GlanceModifier.appWidgetBackground()
                .background(MaterialTheme.colorScheme.background)
                .padding(4.dp)
                .cornerRadius(4.dp)
        ) {
            Text(
                text = "Today's Reminders",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                ),
                modifier = GlanceModifier.background(MaterialTheme.colorScheme.surface)
                    .fillMaxWidth().padding(2.dp)
            )
            Spacer(
                modifier = GlanceModifier.height(8.dp)
            )
            when (reminderData) {
                ReminderData.Loading -> {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is ReminderData.Available -> {
                    LazyColumn {
                        items(reminderData.reminders) { item ->
                            Text(text = item.content)
                            Spacer(modifier = GlanceModifier.height(1.dp))
                        }
                    }
                }

                is ReminderData.Unavailable -> {
                    Box {
                        Text(text = reminderData.message)
                    }
                }
            }


        }


    }

}