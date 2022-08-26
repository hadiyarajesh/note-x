package com.hadiyarajesh.notex.ui.reminders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hadiyarajesh.notex.database.entity.Reminder
import com.hadiyarajesh.notex.database.model.RepetitionStrategy
import com.hadiyarajesh.notex.repository.reminders.RemindersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class RemindersViewModel @Inject constructor(
    private val remindersRepository: RemindersRepository
) : ViewModel() {
    // Get all reminders as soon as collector collect this flow
    val reminders: Flow<PagingData<Reminder>> =
        remindersRepository
            .getAllReminders()
            .cachedIn(viewModelScope)

    fun createReminder(
        title: String,
        reminderTime: Instant,
        repeat: RepetitionStrategy
    ) = viewModelScope.launch {
        remindersRepository.createReminder(
            title, reminderTime, repeat
        )
    }

}
