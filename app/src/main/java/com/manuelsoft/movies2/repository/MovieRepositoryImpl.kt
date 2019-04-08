package com.manuelsoft.movies2.repository

import android.content.Context
import com.manuelsoft.movies2.business.*
import com.manuelsoft.movies2.data.Movie
import com.manuelsoft.movies2.network.RetrofitProvider
import okhttp3.ResponseBody

class MovieRepositoryImpl private constructor(context: Context) : MovieRepository {

    companion object {
        private var instance: MovieRepositoryImpl? = null
        private var LOCK = Object()
        fun getInstance(context: Context): MovieRepository? {
            if (instance == null) {
                synchronized(LOCK) {
                    if (instance == null) {
                        instance = MovieRepositoryImpl(context = context.applicationContext)
                    }
                }
            }

            return instance
        }
    }

    private val movieModule = RetrofitProvider(context)

    override fun loadMovie(callback: MovieRepository.Callback) {
        callback.onProgress(LoadDataResponse.onProgress())
        movieModule.loadMovie(retrofitProviderCallback = object : RetrofitProvider.Callback<Movie> {
            override fun onFailure(throwable: Throwable) {
                callback.onError(
                    LoadDataResponse.onError(
                        LoadDataErrorType.FAILURE,
                        ErrorData(throwable.toString(), null)
                    )
                )
                throwable.printStackTrace()
            }

            override fun onSuccessful(body: Movie?) {
                callback.onSuccess(LoadDataResponse.onResponse(LoadDataType.NETWORK, body))
            }

            override fun onUnsuccessful(code: Int, errorBody: ResponseBody?) {
                callback.onError(
                    LoadDataResponse.onError(
                        LoadDataErrorType.UNSUCCESSFUL,
                        ErrorData(errorBody?.string(), code)
                    )
                )
            }

        })
    }
}