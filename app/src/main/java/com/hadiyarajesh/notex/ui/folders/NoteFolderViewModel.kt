package com.hadiyarajesh.notex.ui.folders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hadiyarajesh.notex.database.model.FolderType
import com.hadiyarajesh.notex.database.model.NoteFolder
import com.hadiyarajesh.notex.repository.folder.FolderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@HiltViewModel
class NoteFolderViewModel @Inject constructor(
    private val folderRepository: FolderRepository
) : ViewModel() {
    fun createFolder(
        name: String,
        description: String?,
        folderType: FolderType
    ) = viewModelScope.launch {
        folderRepository.createFolder(
            name = name,
            description = description,
            folderType = folderType
        )
    }

    fun getFolders(): Flow<PagingData<NoteFolder>> {
        return folderRepository.getAllNoteFolders().cachedIn(viewModelScope)
    }
}