package com.hadiyarajesh.notex.repository.folder

import com.hadiyarajesh.notex.database.dao.FolderDao
import com.hadiyarajesh.notex.database.entity.Folder
import com.hadiyarajesh.notex.database.model.FolderType
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
}
