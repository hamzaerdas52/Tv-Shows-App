package com.hamzaerdas.tvshowsapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.inject.Inject

@Entity
class Favorite(
    var favoriteId: Int
) {
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}
