package com.hamzaerdas.tvshowsapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.inject.Inject


@Entity
data class Favorite (
    @ColumnInfo(name = "favoriteId")
    var favoriteId: Int
){
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}
