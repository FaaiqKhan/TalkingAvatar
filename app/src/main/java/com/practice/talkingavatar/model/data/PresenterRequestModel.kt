package com.practice.talkingavatar.model.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PresenterRequestModel(
    @Json(name = "source_url")
    val sourceUrl: String,
    @Json(name = "driver_url")
    val driverUrl: String,
)