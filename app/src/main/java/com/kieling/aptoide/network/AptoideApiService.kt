package com.kieling.aptoide.network

import com.kieling.aptoide.model.ServerResponse
import retrofit2.http.GET

interface AptoideApiService {
    @GET("listApps")
    suspend fun getApps(): ServerResponse
}


