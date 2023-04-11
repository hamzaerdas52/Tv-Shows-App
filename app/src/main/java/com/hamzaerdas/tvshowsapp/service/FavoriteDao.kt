package com.hamzaerdas.tvshowsapp.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.hamzaerdas.tvshowsapp.model.Favorite

@Dao
interface FavoriteDao {

    @Insert
    suspend fun addFavorite(favorite: Favorite)

    @Query("SELECT COUNT(*) FROM Favorite WHERE favoriteId = :favoriteId")
    suspend fun hasBeenAdded(favoriteId: Int) : Int

    @Query("DELETE FROM Favorite WHERE favoriteId = :favoriteId")
    suspend fun deleteFavorite(favoriteId: Int)
}