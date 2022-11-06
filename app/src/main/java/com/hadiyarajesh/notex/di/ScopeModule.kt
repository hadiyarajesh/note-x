package com.hadiyarajesh.notex.di

import com.hadiyarajesh.notex.database.dao.ReminderDao
import com.hadiyarajesh.notex.reminder.ReminderService
import com.hadiyarajesh.notex.reminder.notification.NotificationHelper
import com.hadiyarajesh.notex.reminder.worker.ReminderWorkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@InstallIn(SingletonComponent::class)
@Module
class ScopeModule {
    @Singleton
    @ApplicationScope
    @Provides
    fun provideCoroutineScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob() + Dispatchers.IO)
    }

    @Singleton
    @Provides
    fun provideNotificationHelper(): NotificationHelper {
        return NotificationHelper()
    }

    @Singleton
    @Provides
    fun provideNotificationService(db: ReminderDao): ReminderService {
        return ReminderWorkManager(db)
    }
}
