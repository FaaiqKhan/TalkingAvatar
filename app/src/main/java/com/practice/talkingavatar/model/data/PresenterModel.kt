package com.practice.talkingavatar.model.data

import androidx.room.*
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = "presenter_model")
@JsonClass(generateAdapter = true)
data class PresenterModel(
    @PrimaryKey
    val id: String,
    @ColumnInfo
    val status: String?,
    @ColumnInfo
    val config: PresenterConfigModel?,
    @Json(name = "user_id")
    @ColumnInfo(name = "user_id")
    val userId: String?,
    @Json(name = "result_url")
    @ColumnInfo(name = "result_url")
    val resultUrl: String?,
    @Json(name = "source_url")
    @ColumnInfo(name = "source_url")
    val sourceUrl: String?,
    @Json(name = "driver_url")
    @ColumnInfo(name = "driver_url")
    val driverUrl: String?,
    @ColumnInfo(name = "file_path")
    val filePath: String? = null,
    @ColumnInfo
    val image: PresenterImage? = null,
)