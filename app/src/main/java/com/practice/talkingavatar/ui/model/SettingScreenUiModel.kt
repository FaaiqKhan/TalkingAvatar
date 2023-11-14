package com.practice.talkingavatar.ui.model

import com.practice.talkingavatar.model.data.DidCreditModel
import com.practice.talkingavatar.model.data.ElevenLabSubscriptionModel

data class SettingScreenUiModel (
    val didCredit: DidCreditModel,
    val elevenLabCredit: ElevenLabSubscriptionModel,
    val totalVoices: Int
)