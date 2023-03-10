package com.hamzaerdas.tvshowsapp.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.hamzaerdas.tvshowsapp.model.Favorite
import com.hamzaerdas.tvshowsapp.model.TvShow
import com.hamzaerdas.tvshowsapp.model.TvShowResponse

@Dao
interface TvShowDao {

    @Insert
    suspend fun addFavorite(favorite: Favorite)

    @Insert
    suspend fun addTvShow(vararg tvShow: TvShow) : List<Long>

    @Query("SELECT * FROM Favorite")
    suspend fun getAllFavorite(): List<Favorite>

    @Query("SELECT * FROM TvShow")
    suspend fun getPopularTvShow(): List<TvShow>

    @Query("DELETE FROM Favorite WHERE favoriteId = :favoriteId")
    suspend fun deleteFavorite(favoriteId: Int)

    @Query("DELETE FROM TvShow")
    suspend fun deleteAllTvShow()
}