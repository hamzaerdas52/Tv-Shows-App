package com.hamzaerdas.tvshowsapp.model

import android.util.ArrayMap
import com.google.gson.annotations.SerializedName
import java.text.DecimalFormat

data class TvShowDetail(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("genres")
    val genres: List<TvShowGenres>,

    @SerializedName("number_of_episodes")
    val episodesCount: Int?,

    @SerializedName("number_of_seasons")
    val seasonsCount: Int?,

    @SerializedName("vote_average")
    var vote: Float?,

    @SerializedName("overview")
    val detail: String?,

    @SerializedName("poster_path")
    val poster: String?
)
