package com.practice.talkingavatar.model.database.dao

import androidx.room.*
import com.practice.talkingavatar.model.data.PresenterModel

@Dao
interface PresenterDao {
    @Insert
    fun insertPresenter(vararg presenterModel: PresenterModel)

    @Query("SELECT * FROM `presenter_model` WHERE id=:id")
    fun getPresenter(id: String): PresenterModel
}
