package com.manuelsoft.movies2.repository

import com.manuelsoft.movies2.data.Configuration
import com.manuelsoft.movies2.data.Movie
import com.manuelsoft.movies2.network.MovieModule


interface Repository {

    fun loadConfiguration(callback: MovieModule.MovieCallback<Configuration>)

    fun loadMovie(callback: MovieModule.MovieCallback<Movie>)




}