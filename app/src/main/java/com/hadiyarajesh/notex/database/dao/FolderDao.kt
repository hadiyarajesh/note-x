package com.hadiyarajesh.notex.database.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.hadiyarajesh.notex.database.entity.Folder
import com.hadiyarajesh.notex.database.model.FolderType
import com.hadiyarajesh.notex.database.model.NoteFolder
import com.hadiyarajesh.notex.database.model.ReminderFolder

@Dao
interface FolderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(folder: Folder): Long

    /**
     * This method requires Room to run two queries, so add the @Transaction annotation to this method to ensure that the whole operation is performed atomically.
     */
    @Transaction
    @Query("SELECT * FROM Folder WHERE folderType = :folderType ORDER BY updatedOn")
    fun getAllNoteFolders(
        folderType: FolderType = FolderType.NoteFolder
    ): PagingSource<Int, NoteFolder>

    @Transaction
    @Query("SELECT * FROM Folder WHERE folderId = :folderId AND folderType = :folderType ORDER BY updatedOn")
    suspend fun getNoteFolder(
        folderId: Long,
        folderType: FolderType = FolderType.NoteFolder
    ): NoteFolder

    @Transaction
    @Query("SELECT * FROM Folder WHERE folderType = :folderType ORDER BY updatedOn")
    fun getAllReminderFolders(
        folderType: FolderType = FolderType.ReminderFolder
    ): PagingSource<Int, ReminderFolder>

    @Transaction
    @Query("SELECT * FROM Folder WHERE folderId = :folderId AND folderType = :folderType ORDER BY updatedOn")
    suspend fun getReminderFolder(
        folderId: Long,
        folderType: FolderType = FolderType.ReminderFolder
    ): ReminderFolder
}
