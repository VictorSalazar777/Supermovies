package com.manuelsoft.movies2.business

import com.manuelsoft.movies2.data.Configuration
import com.manuelsoft.movies2.data.Genre
import com.manuelsoft.movies2.data.GenresResponse
import com.manuelsoft.movies2.data2.DiscoverMoviesResult
import io.reactivex.Single

interface RepositoryRx {

    fun getConfigurationRx() : Single<Configuration>

    fun getGenres() : Single<List<Genre>>

    fun getGenres2() : Single<GenresResponse>

    fun getDiscoveredMoviesRx() : Single<DiscoverMoviesResult>

    fun getDiscoveredMoviesRx(genre: String) : Single<DiscoverMoviesResult>
}