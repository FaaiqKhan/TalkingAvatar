package com.practice.talkingavatar.model.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ElevenLabVoiceModel(
    val voices: List<VoiceModel>
)

@JsonClass(generateAdapter = true)
data class VoiceModel(
    @Json(name = "voice_id")
    val voiceId: String,
    val name: String,
    @Json(name = "preview_url")
    val previewUrl: String,
    val category: String,
    val description: String?,
)