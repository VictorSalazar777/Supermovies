package com.manuelsoft.movies2.network

import android.content.Context
import com.manuelsoft.movies2.R
import com.manuelsoft.movies2.Utils
import com.manuelsoft.movies2.data.Configuration
import com.manuelsoft.movies2.data.Genre
import com.manuelsoft.movies2.data.GenresResponse
import com.manuelsoft.movies2.data.Movie
import com.manuelsoft.movies2.data2.DiscoverMoviesResult
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber

class RetrofitLoads(private val context: Context) {

    private val retrofitService = RetrofitProvider.getInstance(context).getRetrofitService()
    private val retrofitServiceRx = RetrofitProvider.getInstance(context).getRetrofitServiceRx()
    private val apiKey = context.getString(R.string.themoviedb_api_key)


    private fun chooseRandomMovie() : String {
        return (Utils.getRandomDoubleBetweenRange(1.0, 40000.0).toInt()).toString()
    }

    fun loadConfiguration(callback: RetrofitProvider.Callback<Configuration>) {

        val call = retrofitService.getConfiguration(apiKey)

        Timber.i("loadConfiguration: url = %s", call.request().url())
        call.enqueue(object : retrofit2.Callback<Configuration> {
            override fun onFailure(call: Call<Configuration>, t: Throwable) {
                callback.onFailure(t)
            }

            override fun onResponse(call: Call<Configuration>, response: Response<Configuration>) {
                if (response.isSuccessful) {
                    callback.onSuccessful(response.body())
                } else {
                    callback.onUnsuccessful(response.code(), response.errorBody())
                }
            }
        })
    }


    fun loadMovie(retrofitProviderCallback: RetrofitProvider.Callback<Movie>) {
        val call = retrofitService.getMovie(chooseRandomMovie(), apiKey)

        Timber.i("loadMovie: url = %s", call.request().url())
        call.enqueue(object : retrofit2.Callback<Movie> {
            override fun onFailure(call: Call<Movie>, t: Throwable) {
                retrofitProviderCallback.onFailure(t)
            }

            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                if (response.isSuccessful) {
                    retrofitProviderCallback.onSuccessful(response.body())
                } else {
                    retrofitProviderCallback.onUnsuccessful(response.code(), response.errorBody())
                }
            }

        })
    }


    fun <T> loadData(retrofitProviderCallback: RetrofitProvider.Callback<T>, someClass: Class<T>) {
        val call = getCall(someClass)
        Timber.i("load: url = %s", call.request().url())
        call.enqueue(object : retrofit2.Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                retrofitProviderCallback.onFailure(t)
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    retrofitProviderCallback.onSuccessful(response.body())
                } else {
                    retrofitProviderCallback.onUnsuccessful(response.code(), response.errorBody())
                }
            }

        })
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> getCall(someClass: Class<T>): Call<T> {
        return when {
            someClass.isAssignableFrom(Movie::class.java) ->
                retrofitService.getMovie(chooseRandomMovie(), apiKey
                ) as Call<T>

            someClass.isAssignableFrom(Configuration::class.java) ->
                retrofitService.getConfiguration(apiKey) as Call<T>

            else -> throw ClassNotFoundException("Class ${someClass.simpleName} is unknown")
        }
    }


    fun getConfigurationRx() : Single<Configuration> {
        return retrofitServiceRx.getConfigurationRx(apiKey)
    }

    fun getGenres() : Single<List<Genre>> {
        return retrofitServiceRx.getGenres(apiKey)
            .doOnSuccess { t -> Timber.i(t.toString()) }
            .doOnError { e-> Timber.e(e) }
    }

    fun getGenres2() : Single<GenresResponse> {
        return retrofitServiceRx.getGenres2(apiKey)
            .doOnSuccess { t -> Timber.i(t.toString()) }
            .doOnError { e-> Timber.e(e) }
    }

    fun getDiscoveredMoviesRx() : Single<DiscoverMoviesResult> {
        return retrofitServiceRx.discoverMovies(apiKey)
    }

    fun getDiscoveredMoviesRx(genre: String) :  Single<DiscoverMoviesResult> {
        return retrofitServiceRx.discoverMovies(apiKey, genre)
    }


}