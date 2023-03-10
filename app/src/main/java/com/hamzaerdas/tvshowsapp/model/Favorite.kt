package com.hamzaerdas.tvshowsapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favorite(
    @ColumnInfo(name = "favoriteId")
    var favoriteId: Int? = 0
){
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}
