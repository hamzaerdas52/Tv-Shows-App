package com.hamzaerdas.tvshowsapp.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hamzaerdas.tvshowsapp.model.Favorite
import com.hamzaerdas.tvshowsapp.model.TvShow

@Database(entities = [Favorite::class, TvShow::class], version = 4)
abstract class TvShowDatabase : RoomDatabase(){
    abstract fun getTvShowDao() : TvShowDao
    abstract fun getFavoriteDao() : FavoriteDao
}