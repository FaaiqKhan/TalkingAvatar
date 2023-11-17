package com.practice.talkingavatar.model.database.typeConverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.practice.talkingavatar.model.data.PresenterImage

class PresenterImageTypeConverter {

    @TypeConverter
    fun presenterImageToString(presenterImage: PresenterImage) = Gson().toJson(presenterImage)

    @TypeConverter
    fun stringToPresenterImage(value: String): PresenterImage = Gson().fromJson(
        value,
        object : TypeToken<PresenterImage>() {}.type
    )
}