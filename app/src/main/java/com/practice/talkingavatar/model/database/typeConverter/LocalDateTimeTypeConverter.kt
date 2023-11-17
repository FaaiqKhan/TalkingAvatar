package com.practice.talkingavatar.model.database.typeConverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.LocalDateTime

class LocalDateTimeTypeConverter {

    @TypeConverter
    fun localDateTimeToString(localDateTime: LocalDateTime) = Gson().toJson(localDateTime)

    @TypeConverter
    fun stringToLocalDateTime(value: String): LocalDateTime = Gson().fromJson(
        value,
        object : TypeToken<LocalDateTime>() {}.type
    )
}