package com.hadiyarajesh.notex.database.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.hadiyarajesh.notex.database.entity.Reminder
import com.hadiyarajesh.notex.utility.InternalUseOnly
import java.time.Instant

@Dao
interface ReminderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(reminder: Reminder)

    /**
     * This method will add reminder to folder
     */
    @Transaction
    suspend fun addToFolder(reminderId: Long, parentFolderId: Long) {
        attachReminderToFolder(reminderId, parentFolderId)
        updateFolderModificationProperty(parentFolderId)
    }

    @InternalUseOnly
    @Query("UPDATE Reminder SET parentFolderId = :parentFolderId WHERE reminderId = :reminderId")
    suspend fun attachReminderToFolder(reminderId: Long, parentFolderId: Long)

    @InternalUseOnly
    @Query("UPDATE Folder SET updatedOn = :updatedOn WHERE folderId =:folderId ")
    suspend fun updateFolderModificationProperty(folderId: Long, updatedOn: Instant = Instant.now())

    @Query("SELECT * FROM Reminder WHERE reminderId = :reminderId")
    fun getById(reminderId: Long): Reminder

    @Query("SELECT * FROM Reminder ORDER BY reminderId DESC")
    fun getAllByDesc(): PagingSource<Int, Reminder>

    @Delete
    fun delete(reminder: Reminder)

    @Query("DELETE FROM Reminder WHERE reminderId = :reminderId")
    fun deleteById(reminderId: Long)

    /**
     * WARNING: USE WITH CAUTION
     * Permanently delete all reminders
     */
    @Query("DELETE FROM Reminder")
    fun deleteAll()
}
