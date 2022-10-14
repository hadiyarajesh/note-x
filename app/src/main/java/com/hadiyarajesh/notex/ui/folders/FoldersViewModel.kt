package com.hadiyarajesh.notex.ui.folders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hadiyarajesh.notex.database.entity.Folder
import com.hadiyarajesh.notex.database.model.FolderType
import com.hadiyarajesh.notex.database.model.NoteFolder
import com.hadiyarajesh.notex.database.model.ReminderFolder
import com.hadiyarajesh.notex.repository.folder.FolderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoldersViewModel @Inject constructor(
    private val folderRepository: FolderRepository
) : ViewModel() {

    val noteFolders: Flow<PagingData<NoteFolder>> =
        folderRepository.getAllNoteFolders().cachedIn(viewModelScope)

    val reminderFolders : Flow<PagingData<ReminderFolder>> =
        folderRepository.getAllReminderFolders().cachedIn(viewModelScope)


    fun createFolder(
        name: String,
        description: String?,
        folderType: FolderType
    ) = viewModelScope.launch {
        folderRepository.createFolder(
            name, description, folderType
        )
    }
}