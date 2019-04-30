package com.manuelsoft.movies2.repository

import android.content.Context
import com.manuelsoft.movies2.business.Repository
import com.manuelsoft.movies2.data.GenresResponse
import com.manuelsoft.movies2.data2.DiscoverMoviesResult
import com.manuelsoft.movies2.network.RetrofitLoads
import io.reactivex.Maybe
import io.reactivex.Single
import timber.log.Timber

class RepositoryImpl(context: Context) : Repository {

    private val retrofitLoads = RetrofitLoads(context)
    private val genreBag = ArrayList<GenresResponse>(1)

    override fun getGenres(): Single<GenresResponse> {
        return Maybe.concat(
            getGenresFromCache(),
            getGenresFromNetwork().toMaybe()
        ).firstElement().toSingle()
    }

    private fun getGenresFromCache() : Maybe<GenresResponse> {
        return Single.fromCallable<List<GenresResponse>> {
            return@fromCallable genreBag }
            .filter {genreBag.isNotEmpty()}
            .map {genreBag[0]}
            .doOnSuccess {
                Timber.i("getGenresFromCache()")
            }
            .doOnError {
                Timber.i("getGenresFromCache() Error ->$it")
            }

    }

    private fun getGenresFromNetwork() : Single<GenresResponse> {
        return retrofitLoads.getGenres()
            .doOnSuccess {
                genreBag.add(it)
                Timber.i("getGenresFromNetwork()")
            }
    }

    override fun getDiscoveredMovies(): Single<DiscoverMoviesResult> {
        return retrofitLoads.getDiscoveredMovies()
    }

    override fun getDiscoveredMovies(genre: String): Single<DiscoverMoviesResult> {
        return retrofitLoads.getDiscoveredMovies(genre)
    }
}