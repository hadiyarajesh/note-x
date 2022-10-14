package com.hadiyarajesh.notex.repository.folder

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hadiyarajesh.notex.database.dao.FolderDao
import com.hadiyarajesh.notex.database.entity.Folder
import com.hadiyarajesh.notex.database.model.FolderType
import com.hadiyarajesh.notex.database.model.NoteFolder
import com.hadiyarajesh.notex.database.model.ReminderFolder
import kotlinx.coroutines.flow.Flow
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FolderRepository @Inject constructor(
    private val folderDao: FolderDao
) {
    suspend fun createFolder(
        name: String,
        description: String?,
        folderType: FolderType,
    ) {
        folderDao.insertOrUpdate(
            Folder(
                title = name,
                description = description,
                folderType = folderType,
                createdOn = Instant.now(),
                updatedOn = Instant.now()
            )
        )
    }

    fun getAllNoteFolders(): Flow<PagingData<NoteFolder>> = Pager(
        config = PagingConfig(pageSize = 8)//since we want a maximum of 8 folders in one screen
    ){
        folderDao.getAllNoteFolders(FolderType.NoteFolder)
    }.flow

    fun getAllReminderFolders(): Flow<PagingData<ReminderFolder>> = Pager(
        config = PagingConfig(pageSize = 8)//since we want a maximum of 8 folders in one screen
    ){
        folderDao.getAllReminderFolders(FolderType.ReminderFolder)
    }.flow
}
