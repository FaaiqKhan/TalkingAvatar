package com.practice.talkingavatar.model.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PresenterResponseModel(
    val id: String,
    @Json(name = "object")
    val `object`: String,
    @Json(name = "create_at")
    val createdAt: String?,
    @Json(name = "created_by")
    val createBy: String?,
    val status: String,
)
