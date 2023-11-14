package com.practice.talkingavatar.model.repository

import okhttp3.Interceptor
import okhttp3.Response

class ElevenLabServiceCredentialsInterceptor(private val apiKey: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val authenticatedRequest = chain.request().newBuilder()
            .header("Accept", "application/json")
            .header("xi-api-key", apiKey)
            .build()
        return chain.proceed(authenticatedRequest)
    }
}