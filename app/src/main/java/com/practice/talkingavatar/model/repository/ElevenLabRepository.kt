package com.practice.talkingavatar.model.repository

import arrow.core.Either
import com.practice.talkingavatar.model.data.ElevenLabSubscriptionModel
import com.practice.talkingavatar.model.services.ElevenLabService
import javax.inject.Inject

interface ElevenLabRepository {

    suspend fun getSubscription(): Either<String, ElevenLabSubscriptionModel>
    suspend fun getVoices(): Either<String, Int>
}

class DefaultElevenLabRepository @Inject constructor(
    private val elevenLabService: ElevenLabService
) : ElevenLabRepository {

    override suspend fun getSubscription(): Either<String, ElevenLabSubscriptionModel> {
        return try {
            Either.Right(elevenLabService.getSubscription())
        } catch (e: Exception) {
            Either.Left(e.message ?: "Something went wrong")
        }
    }

    override suspend fun getVoices(): Either<String, Int> {
        return try {
            Either.Right(elevenLabService.getVoices().voices.size)
        } catch (e: Exception) {
            Either.Left(e.message ?: "Something went wrong")
        }
    }

}