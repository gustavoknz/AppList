package com.kieling.applist.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kieling.applist.extension.TAG
import com.kieling.applist.repository.ApplistRepository
import kotlinx.coroutines.launch
import java.io.IOException

class AppListViewModel(repository: ApplistRepository) : ViewModel() {
    private val applistRepository: ApplistRepository = repository
    val appList = applistRepository.apps
    var dataState = MutableLiveData<Boolean>()

    init {
        refreshDataFromRepository()
    }

    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                applistRepository.refreshApps()
                dataState.postValue(true)
            } catch (networkError: IOException) {
                if (appList.value.isNullOrEmpty()) {
                    Log.e(TAG, "Error fetching apps: ${networkError.localizedMessage}")
                    dataState.postValue(false)
                }
            }
        }
    }

    // ViewModel Factory
    class Factory(private val repository: ApplistRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AppListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AppListViewModel(repository) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}