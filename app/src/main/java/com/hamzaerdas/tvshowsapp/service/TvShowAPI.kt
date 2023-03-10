package com.hamzaerdas.tvshowsapp.service

import com.hamzaerdas.tvshowsapp.model.TvShow
import com.hamzaerdas.tvshowsapp.model.TvShowDetail
import com.hamzaerdas.tvshowsapp.model.TvShowResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowAPI {

    // https://api.themoviedb.org/3
    // /tv/popular?api_key=260f15b7848e372a5332893b1d39d366&page=1

    @GET("tv/popular?api_key=260f15b7848e372a5332893b1d39d366&page=1")
    fun getPopularTvShowData(): Single<TvShowResponse>

    @GET("tv/popular?api_key=260f15b7848e372a5332893b1d39d366&page=page")
    fun getOtherTvShowData(
        @Query("page") page:Int
    ): Single<TvShowResponse>

    @GET("tv/{tv_id}?api_key=260f15b7848e372a5332893b1d39d366")
    fun getTvShowDetailsData(
        @Path("tv_id") id: Int
    ): Single<TvShowDetail>
}