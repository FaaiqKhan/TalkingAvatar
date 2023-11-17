package com.practice.talkingavatar.model.database

import androidx.room.*
import com.practice.talkingavatar.model.data.PresenterModel
import com.practice.talkingavatar.model.database.dao.PresenterDao
import com.practice.talkingavatar.model.database.typeConverter.*

@Database(
    entities = [PresenterModel::class],
    version = 1
)
@TypeConverters(
    value = [
        PresenterConfigTypeConverter::class,
        PresenterImageTypeConverter::class,
        LocalDateTimeTypeConverter::class,
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun presenterDao(): PresenterDao
}