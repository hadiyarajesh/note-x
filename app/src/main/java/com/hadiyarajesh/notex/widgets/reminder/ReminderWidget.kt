package com.hadiyarajesh.notex.widgets.reminder


import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.*
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
import androidx.glance.unit.ColorProvider
import com.hadiyarajesh.notex.R
import com.hadiyarajesh.notex.database.entity.Reminder
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ReminderWidget : GlanceAppWidget() {

    companion object {
        fun updateWidget(context: Context) {
            ReminderWidgetWorker.enqueueOnce(context)
        }
    }

    override val stateDefinition: GlanceStateDefinition<*>
        get() = ReminderDataStateDefinition


    @Composable
    override fun Content() {
        val reminderData = currentState<ReminderData>()
        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .appWidgetBackground()
                .background(R.color.widget_background_color)
                .padding(12.dp)
                .cornerRadius(24.dp)
        ) {
            Text(
                text = "Reminders",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    fontSize = 18.sp,
                    color = ColorProvider(R.color.widget_text_color)
                ),
                modifier = GlanceModifier
                    .fillMaxWidth().padding(2.dp)
            )
            Spacer(
                modifier = GlanceModifier.height(8.dp)
            )
            when (reminderData) {
                ReminderData.Loading -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = GlanceModifier.fillMaxWidth()
                            .fillMaxHeight()
                    ) {
                        CircularProgressIndicator(
                        )
                    }
                }
                is ReminderData.Available -> {
                    LazyColumn(
                    ) {
                        if (reminderData.reminders.isEmpty()) {
                            item {
                                Text(
                                    text = "No reminders",
                                    modifier = GlanceModifier.fillMaxSize(),
                                    style = TextStyle(textAlign = TextAlign.Center)
                                )
                            }
                        } else {
                            items(reminderData.reminders) { item ->
                                ReminderItem(item)
                            }
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

    @OptIn(ExperimentalUnitApi::class)
    @Composable
    private fun ReminderItem(item: Reminder) {
        Column(
            modifier = GlanceModifier.fillMaxWidth()
        ) {
            Column(
                modifier = GlanceModifier.fillMaxWidth()
                    .height(48.dp)
                    .background(R.color.widget_text_color)
                    .padding(4.dp)
                    .cornerRadius(12.dp)
            ) {
                Text(
                    modifier = GlanceModifier.fillMaxWidth(),
                    text = DateTimeFormatter.ofPattern("d MMM yy h:mm a")
                        .format(LocalDateTime.ofInstant(item.reminderTime, ZoneId.systemDefault())),
                    style = TextStyle(
                        color = ColorProvider(R.color.widget_background_color),
                        fontSize = 12.sp,
                        textAlign = TextAlign.Right
                    ),
                )
                Text(
                    text = item.content, style = TextStyle(
                        color = ColorProvider(R.color.widget_background_color)
                    )
                )
            }
            Spacer( modifier = GlanceModifier.height(4.dp))
        }
    }

}