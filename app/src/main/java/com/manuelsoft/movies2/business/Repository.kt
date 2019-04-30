package com.manuelsoft.movies2.business

import com.manuelsoft.movies2.data.GenresResponse
import com.manuelsoft.movies2.data2.DiscoverMoviesResult
import io.reactivex.Single

interface Repository {

    fun getGenres() : Single<GenresResponse>

    fun getDiscoveredMovies() : Single<DiscoverMoviesResult>

    fun getDiscoveredMovies(genre: String) : Single<DiscoverMoviesResult>
}