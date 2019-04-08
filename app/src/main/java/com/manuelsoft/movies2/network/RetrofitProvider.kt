package com.manuelsoft.movies2.network

import android.content.Context
import com.manuelsoft.movies2.R
import com.manuelsoft.movies2.Utils
import com.manuelsoft.movies2.data.Configuration
import com.manuelsoft.movies2.data.Movie
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

class RetrofitProvider(private val context: Context) {

    interface Callback<T> {
        fun onFailure(throwable: Throwable)
        fun onSuccessful(body: T?)
        fun onUnsuccessful(code: Int, errorBody: ResponseBody?)
    }

    fun provideRetrofitService(): RetrofitService {
        return Retrofit.Builder()
            .baseUrl(context.getString(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitService::class.java)
    }

    fun loadConfiguration(callback: Callback<Configuration>) {
        val retrofitService = provideRetrofitService()
        val call = retrofitService.getConfiguration(context.getString(R.string.themoviedb_api_key))

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


    fun loadMovie(retrofitProviderCallback: Callback<Movie>) {
        val retrofitService = provideRetrofitService()
        val call = retrofitService.getMovie(
            (Utils.getRandomDoubleBetweenRange(1.0, 40000.0).toInt()).toString(),
            context.getString(R.string.themoviedb_api_key)
        )

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

    fun <T> loadData(retrofitProviderCallback: Callback<T>, someClass: Class<T>) {
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

        val retrofitService = provideRetrofitService()
        return when {
            someClass.isAssignableFrom(Movie::class.java) ->
                retrofitService.getMovie(
                    (Utils.getRandomDoubleBetweenRange(1.0, 40000.0).toInt()).toString(),
                    context.getString(R.string.themoviedb_api_key)
                ) as Call<T>

            someClass.isAssignableFrom(Configuration::class.java) ->
                retrofitService.getConfiguration(context.getString(R.string.themoviedb_api_key)) as Call<T>

            else -> throw ClassNotFoundException("Class ${someClass.simpleName} is unknown")
        }
    }
}