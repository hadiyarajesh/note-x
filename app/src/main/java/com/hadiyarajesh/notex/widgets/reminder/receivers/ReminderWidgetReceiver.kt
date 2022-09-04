package com.hadiyarajesh.notex.widgets.reminder.receivers

import android.appwidget.AppWidgetManager
import android.content.Context
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import com.hadiyarajesh.notex.widgets.reminder.ReminderWidget
import com.hadiyarajesh.notex.widgets.reminder.ReminderWidgetWorker

class ReminderWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = ReminderWidget()

    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
        context?.let { ReminderWidgetWorker.enqueue(it) }
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        ReminderWidgetWorker.enqueueOnce(context)

    }

    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
        context?.let { ReminderWidgetWorker.cancel(it) }
    }

}