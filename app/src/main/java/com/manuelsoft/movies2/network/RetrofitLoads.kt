package com.manuelsoft.movies2.network

import android.content.Context
import com.manuelsoft.movies2.R
import com.manuelsoft.movies2.data.GenresResponse
import com.manuelsoft.movies2.data2.DiscoverMoviesResult
import io.reactivex.Single
import timber.log.Timber

class RetrofitLoads(private val context: Context) {

    private val retrofitServiceRx = RetrofitProvider.getInstance(context).getRetrofitService()
    private val apiKey = context.getString(R.string.themoviedb_api_key)

    fun getGenres() : Single<GenresResponse> {
        return retrofitServiceRx.getGenres(apiKey)
            .doOnSuccess { t -> Timber.i(t.toString()) }
            .doOnError { e-> Timber.e(e) }
    }

    fun getDiscoveredMovies() : Single<DiscoverMoviesResult> {
        return retrofitServiceRx.discoverMovies(apiKey)
    }

    fun getDiscoveredMovies(genre: String) :  Single<DiscoverMoviesResult> {
        return retrofitServiceRx.discoverMovies(apiKey, genre)
    }


}