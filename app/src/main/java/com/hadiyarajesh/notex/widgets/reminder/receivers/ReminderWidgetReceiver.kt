package com.hadiyarajesh.notex.widgets.reminder.receivers

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import com.hadiyarajesh.notex.widgets.reminder.ReminderWidget

class ReminderWidgetReceiver: GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = ReminderWidget()

}