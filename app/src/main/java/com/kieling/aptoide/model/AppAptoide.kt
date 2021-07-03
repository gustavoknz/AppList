package com.kieling.aptoide.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "app_table")
data class AppAptoide(

    @PrimaryKey(autoGenerate = false)
    @Json( name= "id")
    var id: Long,

    @ColumnInfo(name = "app_name")
    @Json(name = "name")
    var name: String?
)
