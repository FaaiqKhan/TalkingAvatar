package com.practice.talkingavatar.model.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PresenterConfigModel(
    val mute: Boolean?,
    @Json(name = "result_format")
    val resultFormat: String?,
)

