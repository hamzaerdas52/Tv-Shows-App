package com.hamzaerdas.tvshowsapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class TvShow(
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val id: Int? = 0,

    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String?,

    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    val overview: String? = "Detay Yok",

    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    val vote: Double? = 0.0,

    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    val poster: String?
){
    @PrimaryKey(autoGenerate = true)
    var uuid : Int = 0
}
