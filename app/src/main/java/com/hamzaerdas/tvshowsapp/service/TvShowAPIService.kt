package com.hamzaerdas.tvshowsapp.service

import com.hamzaerdas.tvshowsapp.model.TvShow
import com.hamzaerdas.tvshowsapp.model.TvShowDetail
import com.hamzaerdas.tvshowsapp.model.TvShowResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class TvShowAPIService {

    // https://api.themoviedb.org/3
    // /tv/popular?api_key=260f15b7848e372a5332893b1d39d366&page=1

    private val BASE_URL = "https://api.themoviedb.org/3/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(TvShowAPI::class.java)

    fun getPopularData(): Single<TvShowResponse>{
        return api.getPopularTvShowData()
    }

    fun getOtherData(page: Int): Single<TvShowResponse>{
        return api.getOtherTvShowData(page)
    }

    fun getDataDetail(id: Int): Single<TvShowDetail>{
        return  api.getTvShowDetailsData(id)
    }
}