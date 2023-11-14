package com.practice.talkingavatar.model.repository

import okhttp3.*

class DidServiceBasicAuthInterceptor(userName: String, password: String) : Interceptor {

    private val credentials: String = Credentials.basic(userName, password)

    override fun intercept(chain: Interceptor.Chain): Response {
        val authenticatedRequest = chain.request().newBuilder()
            .header("authorization", credentials)
            .header("accept", "application/json")
            .header("content-type", "application/json")
            .build()
        return chain.proceed(authenticatedRequest)
    }
}