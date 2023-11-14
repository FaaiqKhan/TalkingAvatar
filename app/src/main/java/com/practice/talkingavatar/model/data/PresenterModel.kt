package com.practice.talkingavatar.model.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PresenterModel(
    val id: String?,
    val status: String?,
    val config: Config?,
    @Json(name = "user_id")
    val userId: String?,
    @Json(name = "result_url")
    val resultUrl: String?,
    @Json(name = "source_url")
    val sourceUrl: String?,
    @Json(name = "driver_url")
    val driverUrl: String?,
    val filePath: String? = null,
    val image: PresenterImage? = null,
)

@JsonClass(generateAdapter = true)
data class Config(
    val mute: Boolean?,
    @Json(name = "result_format")
    val resultFormat: String?,
)
