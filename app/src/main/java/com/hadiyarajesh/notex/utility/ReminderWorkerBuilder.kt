package com.hadiyarajesh.notex.ui.testreminder

import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import com.hadiyarajesh.notex.workmanager.ReminderWorker
import java.util.concurrent.TimeUnit

class ReminderWorkerBuilder  {

    fun createOneTimeWorkRequest(
        delay: Long,
        data: Data,
        tag: String? =null,
    ): OneTimeWorkRequest {

        return OneTimeWorkRequest.Builder(ReminderWorker::class.java)
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .addTag(tag.toString())
            .setInputData(data)
            .build()
    }



}