package com.kieling.applist.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kieling.applist.model.AppApplist

@Database(
    entities = [AppApplist::class],
    version = ApplistDatabase.VERSION,
    exportSchema = false
)
abstract class ApplistDatabase : RoomDatabase() {
    companion object {
        const val VERSION = 1
    }

    abstract fun getApplistDao(): ApplistDao
}