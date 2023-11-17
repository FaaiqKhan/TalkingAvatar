package com.practice.talkingavatar.model.database.typeConverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.practice.talkingavatar.model.data.PresenterConfigModel

class PresenterConfigTypeConverter {

    @TypeConverter
    fun presenterConfigToString(presenterConfig: PresenterConfigModel) =
        Gson().toJson(presenterConfig)

    @TypeConverter
    fun stringToPresenterConfig(value: String): PresenterConfigModel = Gson().fromJson(
        value,
        object : TypeToken<PresenterConfigModel>() {}.type
    )
}