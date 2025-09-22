package com.example.welonekai

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AquariumListViewModel(private val dao: AquariumDao) : ViewModel() {

    val allAquariums = dao.getAllAquariums().asLiveData()

    fun insert(aquarium: Aquarium) = viewModelScope.launch {
        dao.insertAquarium(aquarium)
    }
}

class AquariumViewModelFactory(private val dao: AquariumDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AquariumListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AquariumListViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
