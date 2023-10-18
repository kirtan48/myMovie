package com.example.mymovies.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Movie::class), version = 1, exportSchema = false)
abstract class MovieDatabse :RoomDatabase() {
    abstract fun getMovieDao(): MovieDao


    // Singleton prevents multiple instances of database opening at the
    // same time.
    companion object {



    @Volatile
    private var INSTANCE: MovieDatabse? = null

    fun getDatabase(context: Context): MovieDatabse {
        // if the INSTANCE is not null, then return it,
        // if it is, then create the database
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                MovieDatabse::class.java,
                "movie_database"
            ).build()
            INSTANCE = instance
            // return instance
            instance
        }
    }
}
}
