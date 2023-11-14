package com.practice.talkingavatar.model.services

import com.practice.talkingavatar.model.data.*
import retrofit2.http.*

interface DidService {

    @POST("/animations")
    suspend fun createPresenter(@Body payload: PresenterRequestModel): PresenterResponseModel

    @GET("/animations/{id}")
    suspend fun getPresenter(@Path("id") id: String): PresenterModel

    @POST("/talks")
    suspend fun createTalkFromText(imageUrl: String, voiceId: String, text: String): CreateTalkModel

    @GET("/talks")
    suspend fun getTalk(@Query("id") id: String): TalkModel

    @GET("/credits")
    suspend fun getCredits(): DidCreditModel
}