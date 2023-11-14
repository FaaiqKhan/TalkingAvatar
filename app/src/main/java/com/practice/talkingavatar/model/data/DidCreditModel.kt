package com.practice.talkingavatar.model.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DidCreditModel (
    val credits: List<Credit>,
    val remaining: Int,
    val total: Int,
)

@JsonClass(generateAdapter = true)
data class Credit (
    @Json(name = "owner_id")
    val ownerId: String,
    val remaining: String,
    val total: String,
    @Json(name = "expire_at")
    val expireAt: String,
)