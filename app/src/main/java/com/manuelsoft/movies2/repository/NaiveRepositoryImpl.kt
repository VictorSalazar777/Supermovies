package com.manuelsoft.movies2.repository

import android.content.Context
import com.manuelsoft.movies2.data.Configuration
import com.manuelsoft.movies2.data.Movie
import com.manuelsoft.movies2.network.MovieModule

class NaiveRepositoryImpl private constructor(context: Context) : Repository {

    companion object {
        private var instance: NaiveRepositoryImpl? = null
        private var LOCK = Object()
        fun getInstance(context: Context) : Repository? {
            if (instance == null) {
                synchronized(LOCK) {
                    if (instance == null) {
                        instance = NaiveRepositoryImpl(context = context.applicationContext)
                    }
                }
            }

            return instance
        }
    }

    private val movieModule = MovieModule(context)

    override fun loadConfiguration(callback: MovieModule.MovieCallback<Configuration>) {
        movieModule.loadConfiguration(callback)
    }

    override fun loadMovie(callback: MovieModule.MovieCallback<Movie>) {
        movieModule.loadMovie(callback)
    }


}