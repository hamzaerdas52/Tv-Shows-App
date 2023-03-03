package com.hamzaerdas.tvshowsapp.model

import com.google.gson.annotations.SerializedName

data class TvShow(
    @SerializedName("id")
    val id: Int? = 0,

    @SerializedName("name")
    val name: String?,

    @SerializedName("overview")
    val overview: String? = "Detay Yok",

    @SerializedName("vote_average")
    val vote: Double? = 0.0,

    @SerializedName("poster_path")
    val poster: String?
)
