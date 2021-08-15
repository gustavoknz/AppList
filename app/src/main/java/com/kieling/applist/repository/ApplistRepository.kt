package com.kieling.applist.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.kieling.applist.db.ApplistDao
import com.kieling.applist.extension.TAG
import com.kieling.applist.model.AppApplist
import com.kieling.applist.network.ApplistApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ApplistRepository(
    private val applistDao: ApplistDao,
    private val applistApiService: ApplistApiService
) {
    val apps: LiveData<List<AppApplist>> = applistDao.getAll()

    suspend fun refreshApps() {
        withContext(Dispatchers.IO) {
            Log.d(TAG, "Fetching apps...")
            val serverResponse = applistApiService.getApps()
            Log.d(TAG, "New apps (${serverResponse}): $serverResponse")
            serverResponse.responses?.listApps?.datasets?.all?.data?.list?.let {
                applistDao.insertAll(it)
            }
        }
    }
}