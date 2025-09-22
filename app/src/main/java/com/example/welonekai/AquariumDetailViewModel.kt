package com.example.welonekai

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AquariumDetailViewModel(private val dao: AquariumDao, private val aquariumId: Long) : ViewModel() {

    val aquarium: LiveData<Aquarium> = dao.getAquariumById(aquariumId).asLiveData()
    val logEntries: LiveData<List<LogEntry>> = dao.getLogEntriesForAquarium(aquariumId).asLiveData()

    fun insertLogEntry(logEntry: LogEntry) = viewModelScope.launch {
        dao.insertLogEntry(logEntry)
    }
}

class AquariumDetailViewModelFactory(private val dao: AquariumDao, private val aquariumId: Long) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AquariumDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AquariumDetailViewModel(dao, aquariumId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
