package com.practice.talkingavatar.model.services

import com.practice.talkingavatar.model.data.ElevenLabSubscriptionModel
import com.practice.talkingavatar.model.data.ElevenLabVoiceModel
import retrofit2.http.GET

interface ElevenLabService {

    @GET("user/subscription")
    suspend fun getSubscription(): ElevenLabSubscriptionModel

    @GET("voices")
    suspend fun getVoices(): ElevenLabVoiceModel
}