package com.example.financei.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.financei.database.models.Count
import com.example.financei.repositories.CountRepository
import kotlinx.coroutines.launch

class CountViewModel(private val repository: CountRepository) : ViewModel() {

    val allCounts : LiveData<List<Count>> = repository.allCounts.asLiveData()

    fun insert(count: Count) = viewModelScope.launch {
        repository.insert(count)
    }

}

class CountViewModelFactory(private val repository: CountRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CountViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CountViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}