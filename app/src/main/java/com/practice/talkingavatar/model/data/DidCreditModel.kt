package com.practice.talkingavatar.model.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DidCreditModel(
    val credits: List<Credit>,
    val remaining: Int,
    val total: Int,
)