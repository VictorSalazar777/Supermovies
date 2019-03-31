package com.manuelsoft.movies2.repository

import android.content.Context
import com.manuelsoft.movies2.data.Configuration
import com.manuelsoft.movies2.data.Movie
import com.manuelsoft.movies2.network.MovieModule

class NaiveRepositoryImpl(context: Context) : Repository {

    private val movieModule = MovieModule(context)

    override fun loadConfiguration(callback: MovieModule.MovieCallback<Configuration>) {
        movieModule.loadConfiguration(callback)
    }

    override fun loadMovie(callback: MovieModule.MovieCallback<Movie>) {
        movieModule.loadMovie(callback)
    }


}