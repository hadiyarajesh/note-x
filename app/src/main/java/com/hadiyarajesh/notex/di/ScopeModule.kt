package com.hadiyarajesh.notex.di

import com.hadiyarajesh.notex.reminder.notification.NotificationHelper
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
    fun provideNotificationHelper(): NotificationHelper{
        return NotificationHelper()
    }
}
