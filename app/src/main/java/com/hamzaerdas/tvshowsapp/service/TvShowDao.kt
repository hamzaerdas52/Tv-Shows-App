package com.hamzaerdas.tvshowsapp.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.hamzaerdas.tvshowsapp.model.Favorite
import com.hamzaerdas.tvshowsapp.model.TvShow

@Dao
interface TvShowDao {

    @Insert
    suspend fun addTvShow(vararg tvShow: TvShow) : List<Long>

    @Query("SELECT * FROM TvShow")
    suspend fun getPopularTvShow(): List<TvShow>

    @Query("DELETE FROM TvShow")
    suspend fun deleteAllTvShow()
}