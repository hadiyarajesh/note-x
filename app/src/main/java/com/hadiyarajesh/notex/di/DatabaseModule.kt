package com.hadiyarajesh.notex.di

import android.content.Context
import androidx.room.Room
import com.hadiyarajesh.notex.R
import com.hadiyarajesh.notex.database.NoteXDatabase
import com.hadiyarajesh.notex.database.dao.NoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideNoteXDatabase(@ApplicationContext context: Context): NoteXDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            NoteXDatabase::class.java,
            "${context.getString(R.string.app_name)}.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteDao(db: NoteXDatabase): NoteDao = db.noteDao
}
