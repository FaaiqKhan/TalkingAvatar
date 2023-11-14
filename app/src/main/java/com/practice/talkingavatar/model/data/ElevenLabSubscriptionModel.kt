package com.practice.talkingavatar.model.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ElevenLabSubscriptionModel(
    @Json(name = "allowed_to_extend_character_limit")
    val allowedToExtendCharacterLimit: Boolean?,
    @Json(name = "can_extend_character_limit")
    val canExtendCharacterLimit: Boolean?,
    @Json(name = "can_extend_voice_limit")
    val canExtendVoiceLimit: Boolean?,
    @Json(name = "can_use_instant_voice_cloning")
    val canUseInstantVoiceCloning: Boolean?,
    @Json(name = "can_use_professional_voice_cloning")
    val canUseProfessionalVoiceCloning: Boolean?,
    @Json(name = "character_count")
    val characterCount: Int?,
    @Json(name = "character_limit")
    val characterLimit: Int?,
    val currency: String?,
    @Json(name = "has_open_invoices")
    val hasOpenInvoices: Boolean?,
    @Json(name = "max_voice_add_edits")
    val maxVoiceAddEdits: Int?,
    @Json(name = "next_character_count_reset_unix")
    val nextCharacterCountResetUnix: Int?,
    @Json(name = "professional_voice_limit")
    val professionalVoiceLimit: Int?,
    val status: String?,
    val tier: String?,
    @Json(name = "voice_add_edit_counter")
    val voiceAddEditCounter: Int?,
    @Json(name = "voice_limit")
    val voiceLimit: Int?,
)