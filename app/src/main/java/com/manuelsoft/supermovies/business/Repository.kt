package com.manuelsoft.supermovies.business

import com.manuelsoft.supermovies.data.GenresResponse
import com.manuelsoft.supermovies.data.DiscoverMoviesResult
import io.reactivex.Single

interface Repository {

    fun getGenres() : Single<GenresResponse>

    fun getDiscoveredMovies() : Single<DiscoverMoviesResult>

    fun getDiscoveredMovies(genre: String) : Single<DiscoverMoviesResult>
}