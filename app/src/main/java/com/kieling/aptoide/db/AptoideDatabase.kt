package com.kieling.aptoide.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kieling.aptoide.model.AppAptoide

@Database(
    entities = [AppAptoide::class],
    version = AptoideDatabase.VERSION,
    exportSchema = false
)
abstract class AptoideDatabase : RoomDatabase() {
    companion object {
        const val VERSION = 1
    }

    abstract fun getAptoideDao(): AptoideDao
}