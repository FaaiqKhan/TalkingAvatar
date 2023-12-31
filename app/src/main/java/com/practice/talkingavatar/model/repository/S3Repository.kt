package com.practice.talkingavatar.model.repository

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.PutObjectRequest
import com.practice.talkingavatar.BuildConfig
import java.io.File
import java.time.Instant
import java.util.Date
import javax.inject.Inject

fun interface S3Repository {

    suspend fun generatePreSignedUrlAndUploadImage(file: File, expirationTime: Instant): String
}

class DefaultS3Repository @Inject constructor(
    private val amazonS3Client: AmazonS3Client,
) : S3Repository {

    override suspend fun generatePreSignedUrlAndUploadImage(
        file: File,
        expirationTime: Instant
    ): String {
        try {
            val preSignedUrl = amazonS3Client.generatePresignedUrl(
                BuildConfig.S3_BUCKET_NAME,
                file.absolutePath,
                Date.from(expirationTime),
            ).toString()

            amazonS3Client.putObject(
                PutObjectRequest(
                    BuildConfig.S3_BUCKET_NAME,
                    file.absolutePath,
                    file
                )
            )

            return preSignedUrl
        } catch (e: Exception) {
            throw e
        }
    }

}