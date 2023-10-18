package com.example.mymovies.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_list")
class Movie(val name:String,val director:String, val image: ByteArray) {
    @PrimaryKey(autoGenerate = true)var  id=0
}