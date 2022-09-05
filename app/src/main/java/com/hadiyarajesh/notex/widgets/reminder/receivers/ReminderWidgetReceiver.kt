package com.hadiyarajesh.notex.widgets.reminder.receivers

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import com.hadiyarajesh.notex.widgets.reminder.ReminderWidget
import com.hadiyarajesh.notex.widgets.reminder.ReminderWidgetWorker

class ReminderWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = ReminderWidget()

    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
        context?.let {
            //Load data instantly after widget created
            ReminderWidgetWorker.enqueueOnce(it)
            //schedule periodic data updation.
            ReminderWidgetWorker.enqueue(it)
        }
    }


    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
        context?.let { ReminderWidgetWorker.cancel(it) }
    }

}