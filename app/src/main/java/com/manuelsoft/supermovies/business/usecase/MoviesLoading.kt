package com.manuelsoft.supermovies.business.usecase

import com.manuelsoft.supermovies.business.Repository
import com.manuelsoft.supermovies.data.MovieResult
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