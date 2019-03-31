package com.manuelsoft.movies2.network

import android.content.Context
import com.manuelsoft.movies2.R
import com.manuelsoft.movies2.data.Configuration
import com.manuelsoft.movies2.data.Movie
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

class MovieModule(private val context: Context) {

    interface MovieCallback<T> {
        fun onFailure(throwable: Throwable)
        fun onSuccessful(body: T?, code: Int)
        fun onUnsuccessful(code: Int, errorBody: ResponseBody?)
    }

    fun provideMovieService(): MovieService {
        return Retrofit.Builder()
            .baseUrl(context.getString(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieService::class.java)
    }

    fun loadConfiguration(callback: MovieCallback<Configuration>) {
        val movieService = provideMovieService()
        val call = movieService.getConfiguration(context.getString(R.string.themoviedb_api_key))

        Timber.i("loadConfiguration: url = %s", call.request().url())
        call.enqueue(object: Callback<Configuration> {
            override fun onFailure(call: Call<Configuration>, t: Throwable) {
                callback.onFailure(t)
            }

            override fun onResponse(call: Call<Configuration>, response: Response<Configuration>) {
                if (response.isSuccessful) {
                    callback.onSuccessful(response.body(), response.code())
                } else {
                    callback.onUnsuccessful(response.code(), response.errorBody())
                }
            }
        })
    }


    fun loadMovie(callback: MovieCallback<Movie>) {
        val movieService = provideMovieService()
        val call = movieService.getMovie(context.getString(R.string.themoviedb_api_key))

        Timber.i("loadMovie: url = %s", call.request().url())
        call.enqueue(object: Callback<Movie> {
            override fun onFailure(call: Call<Movie>, t: Throwable) {
                callback.onFailure(t)
            }
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                if (response.isSuccessful) {
                    callback.onSuccessful(response.body(), response.code())
                } else {
                    callback.onUnsuccessful(response.code(), response.errorBody())
                }
            }

        })
    }
}