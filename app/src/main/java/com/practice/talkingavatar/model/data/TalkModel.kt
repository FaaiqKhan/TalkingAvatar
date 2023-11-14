package com.practice.talkingavatar.model.data

data class TalkModel(
    val audioUrl: String,
    val config: TalkModelConfig,
    val driverUrl: String,
    val duration: Int,
    val id: String,
    val resultUrl: String,
    val sourceUrl: String,
    val status: String,
    val userModel: TalkModelUser,
    val userId: String,
)

data class TalkModelConfig (
    val fluent: Boolean,
    val padAudio: Int,
    val reduceNoise: Boolean,
    val resultFormat: String,
    val stitch: Boolean,
)

data class TalkModelUser (
    val email: String,
    val id: String,
    val ownerId: String,
    val plan: String
)
