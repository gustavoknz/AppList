package com.kieling.applist.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "app_table")
data class AppApplist(

    @PrimaryKey(autoGenerate = false)
    @Json( name= "id")
    var id: Long,

    @ColumnInfo(name = "app_name")
    @Json(name = "name")
    var name: String?,

    @ColumnInfo(name = "app_rating")
    @Json(name = "rating")
    var rating: Float?,

    @ColumnInfo(name = "app_icon")
    @Json(name = "icon")
    var icon: String?
)
