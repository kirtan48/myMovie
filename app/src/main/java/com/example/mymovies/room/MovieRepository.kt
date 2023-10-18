package com.example.mymovies.room

import androidx.lifecycle.LiveData
import com.example.mymovies.room.Movie
import com.example.mymovies.room.MovieDao

class MovieRepository(private val movieDao: MovieDao) {
    val allMovie:LiveData<List<Movie>> = movieDao.getAllMovies()

    suspend fun  insert(movie: Movie){
        movieDao.insert(movie)
    }
    suspend fun  delete(movie: Movie){
        movieDao.delete(movie)
    }
}