package com.hadiyarajesh.notex.di

import android.content.Context
import com.hadiyarajesh.notex.notification.LocalNotificationBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

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
    @ApplicationScope
    @Provides
    fun provideNotificationBuilder(@ApplicationContext context: Context): LocalNotificationBuilder {
        return LocalNotificationBuilder(context)
    }

}
