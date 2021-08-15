package com.kieling.applist.network

import com.kieling.applist.model.ServerResponse
import retrofit2.http.GET

interface ApplistApiService {
    @GET("listApps")
    suspend fun getApps(): ServerResponse
}


