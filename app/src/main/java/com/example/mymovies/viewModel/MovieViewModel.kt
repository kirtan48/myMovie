package com.example.mymovies.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.mymovies.room.Movie
import com.example.mymovies.room.MovieDatabse
import com.example.mymovies.room.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel(application: Application) :AndroidViewModel(application) {
    private val repository: MovieRepository
    val allMovie:LiveData<List<Movie>>

    init{
        val dao= MovieDatabse.getDatabase(application).getMovieDao()
        repository= MovieRepository(dao)
        allMovie=repository.allMovie
    }
    fun deleteMovie(movie: Movie)=viewModelScope.launch(Dispatchers.IO){
        repository.delete(movie)

    }
    fun insertMovie(movie: Movie)=viewModelScope.launch (Dispatchers.IO){
        repository.insert(movie)
    }
}