package com.practice.talkingavatar.model.data

import java.time.LocalDateTime

data class PresenterImage(
    val id: String,
    val url: String,
    val expiry: LocalDateTime,
    val filePath: String,
)
