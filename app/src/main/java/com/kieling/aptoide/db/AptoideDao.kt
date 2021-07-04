package com.kieling.aptoide.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kieling.aptoide.model.AppAptoide

@Dao
interface AptoideDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insertAll(aptoide: List<AppAptoide>)

    @Query("SELECT * FROM app_table ORDER BY app_name ASC")
    fun getAll(): LiveData<List<AppAptoide>>

    @Query("SELECT * FROM app_table WHERE id = :id")
    fun getApp(id: Long): AppAptoide

    @Query("DELETE FROM app_table")
    fun clear()
}