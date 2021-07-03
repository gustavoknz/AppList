package com.kieling.aptoide.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.kieling.aptoide.db.AptoideDao
import com.kieling.aptoide.extension.TAG
import com.kieling.aptoide.model.AppAptoide
import com.kieling.aptoide.network.AptoideApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AptoideRepository(
    private val aptoideDao: AptoideDao,
    private val aptoideApiService: AptoideApiService
) {
    val apps: LiveData<List<AppAptoide>> = aptoideDao.getAll()

    suspend fun refreshApps() {
        withContext(Dispatchers.IO) {
            Log.d(TAG, "Fetching apps...")
            val serverResponse = aptoideApiService.getApps()
            Log.d(TAG, "New apps (${serverResponse}): $serverResponse")
            serverResponse.responses?.listApps?.datasets?.all?.data?.list?.let {
                aptoideDao.insertAll(it)
            }
        }
    }
}