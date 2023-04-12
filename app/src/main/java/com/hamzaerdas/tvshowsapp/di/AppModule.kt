package com.hamzaerdas.tvshowsapp.di

import android.content.Context
import androidx.room.Room
import com.hamzaerdas.tvshowsapp.model.Favorite
import com.hamzaerdas.tvshowsapp.service.TvShowAPI
import com.hamzaerdas.tvshowsapp.service.TvShowDatabase
import com.hamzaerdas.tvshowsapp.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun getApi(): TvShowAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(TvShowAPI::class.java)
    }

    @Provides
    @Singleton
    fun createDatabase(@ApplicationContext context: Context): TvShowDatabase {
        return Room.databaseBuilder(
            context,
            TvShowDatabase::class.java,
            "tvshowdatabase"
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideTvShowDao(database: TvShowDatabase) = database.getTvShowDao()

    @Provides
    @Singleton
    fun provideFavoriteDao(database: TvShowDatabase) = database.getFavoriteDao()

}