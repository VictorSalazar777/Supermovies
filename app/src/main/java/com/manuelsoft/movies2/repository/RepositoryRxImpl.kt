package com.manuelsoft.movies2.repository

import android.content.Context
import androidx.lifecycle.Transformations.map
import com.manuelsoft.movies2.business.RepositoryRx
import com.manuelsoft.movies2.data.Configuration
import com.manuelsoft.movies2.data.Genre
import com.manuelsoft.movies2.data.GenresResponse
import com.manuelsoft.movies2.data2.DiscoverMoviesResult
import com.manuelsoft.movies2.network.RetrofitLoads
import io.reactivex.Maybe
import io.reactivex.Single
import timber.log.Timber
import java.util.concurrent.Callable

class RepositoryRxImpl(context: Context) : RepositoryRx {


    private val retrofitLoads = RetrofitLoads(context)
    private val configurationBag = ArrayList<Configuration>(1)
    private val genreBag = ArrayList<List<Genre>>(1)
    private val genreBag2 = ArrayList<GenresResponse>(1)


    override fun getConfigurationRx(): Single<Configuration> {
        return Maybe.concat(
            getConfigurationFromCache(),
            getConfigurationFromNetwork().toMaybe()
        ).firstElement().toSingle()
    }

    private fun getConfigurationFromCache() : Maybe<Configuration> {
        return Single.fromCallable<List<Configuration>>(
            Callable { return@Callable configurationBag }
        )
            .filter { configurationBag.isNotEmpty() }
            .map { configurationBag[0] }
            .doOnSuccess{
                Timber.i("CACHE")
            }
    }

    fun getConfigurationFromNetwork() : Single<Configuration> {
        return retrofitLoads.getConfigurationRx()
            .doOnSuccess {
                configurationBag.add(it)
                Timber.i("NETWORK")
            }
    }

    private fun getGenresFromNetwork() : Single<List<Genre>> {
        return retrofitLoads.getGenres()
            .doOnSuccess {
                genreBag.add(it)
            }
    }

    private fun getGenresFromCache() : Maybe<List<Genre>> {
        return Single.fromCallable<List<List<Genre>>> {
            return@fromCallable genreBag }
            .filter{genreBag.isNotEmpty()}
            .map{genreBag[0]}
    }

    override fun getGenres(): Single<List<Genre>> {
        return Maybe.concat (
            getGenresFromCache(), getGenresFromNetwork().toMaybe()
        ).firstElement().toSingle()
    }

    override fun getGenres2(): Single<GenresResponse> {
        return Maybe.concat(
            getGenres2FromCache(),
            getGenres2FromNetwork().toMaybe()
        ).firstElement().toSingle()
    }

    private fun getGenres2FromCache() : Maybe<GenresResponse> {
        return Single.fromCallable<List<GenresResponse>> {
            return@fromCallable genreBag2 }
            .filter {genreBag2.isNotEmpty()}
            .map {genreBag2[0]}
            .doOnSuccess {
                Timber.i("getGenres2FromCache()")
            }
            .doOnError {
                Timber.i("getGenres2FromCache() Error ->$it")
            }

    }

    private fun getGenres2FromNetwork() : Single<GenresResponse> {
        return retrofitLoads.getGenres2()
            .doOnSuccess {
                genreBag2.add(it)
                Timber.i("getGenres2FromNetwork()")
            }
    }

    override fun getDiscoveredMoviesRx(): Single<DiscoverMoviesResult> {
        return retrofitLoads.getDiscoveredMoviesRx()
    }

    override fun getDiscoveredMoviesRx(genre: String): Single<DiscoverMoviesResult> {
        return retrofitLoads.getDiscoveredMoviesRx(genre)
    }
}