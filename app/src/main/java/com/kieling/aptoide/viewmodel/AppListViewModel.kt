package com.kieling.aptoide.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kieling.aptoide.extension.TAG
import com.kieling.aptoide.repository.AptoideRepository
import kotlinx.coroutines.launch
import java.io.IOException

class AppListViewModel(repository: AptoideRepository) : ViewModel() {
    private val aptoideRepository: AptoideRepository = repository
    val appList = aptoideRepository.apps
    var dataState = MutableLiveData<Boolean>()

    init {
        refreshDataFromRepository()
    }

    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                aptoideRepository.refreshApps()
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
    class Factory(private val repository: AptoideRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AppListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AppListViewModel(repository) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}