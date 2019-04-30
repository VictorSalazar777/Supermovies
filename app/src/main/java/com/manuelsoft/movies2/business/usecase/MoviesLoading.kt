package com.manuelsoft.movies2.business.usecase

import com.manuelsoft.movies2.business.Repository
import com.manuelsoft.movies2.data2.MovieResult
import io.reactivex.Single

class MoviesLoading(private val repositoryRx: Repository) {

    fun getMoviesResult() : Single<List<MovieResult>> {
        return repositoryRx.getDiscoveredMovies().map{
            it.movieListResult
        }
    }

    fun getMoviesResult(genre: String) : Single<List<MovieResult>> {
        return repositoryRx.getDiscoveredMovies(genre).map{
            it.movieListResult
        }
    }


}