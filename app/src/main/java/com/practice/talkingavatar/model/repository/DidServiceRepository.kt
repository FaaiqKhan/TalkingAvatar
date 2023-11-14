package com.practice.talkingavatar.model.repository

import arrow.core.Either
import com.practice.talkingavatar.model.data.*
import com.practice.talkingavatar.model.services.DidService
import com.practice.talkingavatar.utils.Utils
import retrofit2.HttpException
import javax.inject.Inject

interface DidServiceRepository {
    suspend fun generatePresenter(imagePreSignedUrl: String): Either<PresenterErrorModel, PresenterResponseModel>
    suspend fun getPresenter(id: String): Either<PresenterErrorModel, PresenterModel>
    suspend fun createTalkFromAudio(imageUrl: String, driverUrl: String, audioUrl: String)
    suspend fun createTalkFromText(imageUrl: String, driverUrl: String, text: String)
    suspend fun getTalk(id: String)
    suspend fun getDidCredits(): Either<PresenterErrorModel, DidCreditModel>
}

class DefaultDidServiceRepository @Inject constructor(
    private val didService: DidService,
) : DidServiceRepository {

    override suspend fun generatePresenter(imagePreSignedUrl: String): Either<PresenterErrorModel, PresenterResponseModel> {
        try {
            return Either.Right(
                didService.createPresenter(
                    payload = PresenterRequestModel(
                        sourceUrl = imagePreSignedUrl,
                        driverUrl = "bank://nostalgia"
                    )
                )
            )
        } catch (e: Exception) {
            when (e) {
                is HttpException -> {
                    return when (e.code()) {
                        400, 401, 402, 403 -> Either.Left(Utils.parseDIDServiceError(e))
                        else -> {
                            Either.Left(
                                PresenterErrorModel(
                                    kind = "Unknown",
                                    description = "Something went wrong"
                                )
                            )
                        }
                    }
                }

                else -> throw e
            }
        }
    }

    override suspend fun getPresenter(id: String): Either<PresenterErrorModel, PresenterModel> {
        return try {
            Either.Right(didService.getPresenter(id))
        } catch (e: Exception) {
            Either.Left(Utils.parseDIDServiceError(e))
        }
    }

    override suspend fun createTalkFromAudio(
        imageUrl: String,
        driverUrl: String,
        audioUrl: String
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun createTalkFromText(imageUrl: String, driverUrl: String, text: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getTalk(id: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getDidCredits(): Either<PresenterErrorModel, DidCreditModel> {
        return try {
            Either.Right(didService.getCredits())
        } catch (e: Exception) {
            when (e) {
                is HttpException -> {
                    return when (e.code()) {
                        404 -> Either.Left(Utils.parseDIDServiceError(e))
                        else ->
                            Either.Left(
                                PresenterErrorModel(
                                    kind = "Unknown",
                                    description = "Something went wrong"
                                )
                            )
                    }
                }

                else -> throw e
            }
        }
    }
}