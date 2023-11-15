package com.example.flo

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "AlbumTable")
data class Album(
    var title: String?= "",
    var singer: String? = "",
    var coverImg: Int? = null,
    //var songs: ArrayList<Song>? = null
){
    @PrimaryKey(autoGenerate = false) var id: Int = 0
}
