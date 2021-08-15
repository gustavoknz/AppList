package com.kieling.applist.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kieling.applist.model.AppApplist

@Dao
interface ApplistDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insertAll(applist: List<AppApplist>)

    @Query("SELECT * FROM app_table ORDER BY app_name ASC")
    fun getAll(): LiveData<List<AppApplist>>

    @Query("SELECT * FROM app_table WHERE id = :id")
    fun getApp(id: Long): AppApplist

    @Query("DELETE FROM app_table")
    fun clear()
}