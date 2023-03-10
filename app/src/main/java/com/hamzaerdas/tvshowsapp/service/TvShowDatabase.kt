package com.hamzaerdas.tvshowsapp.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hamzaerdas.tvshowsapp.model.Favorite
import com.hamzaerdas.tvshowsapp.model.TvShow

@Database(entities = arrayOf(Favorite::class, TvShow::class), version = 3)
abstract class TvShowDatabase : RoomDatabase(){

    abstract fun getTvShowDao() : TvShowDao

    companion object{
        @Volatile private var instance : TvShowDatabase? = null

        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            TvShowDatabase::class.java,
            "tvshowdatabase")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()


    }
}