package com.manuelsoft.movies2.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.manuelsoft.movies2.business.usecase.ErrorType
import com.manuelsoft.movies2.business.usecase.LoadUseCase
import com.manuelsoft.movies2.business.usecase.UseCase
import com.manuelsoft.movies2.data.Configuration
import com.manuelsoft.movies2.data.Movie
import com.manuelsoft.movies2.entity.MovieSummary
import timber.log.Timber

sealed class LoadMovieResponse {
    data class Success(val movieSummary: MovieSummary) : LoadMovieResponse()
    data class Error(val msg: String) : LoadMovieResponse()
    object Progressing : LoadMovieResponse()
}

class MainActivityViewModel(private val loadUseCase: LoadUseCase) : ViewModel() {

    private var configuration: Configuration? = null

    private val loadMovieWasSuccessful = MutableLiveData<LoadMovieResponse>()
    private var loadConfigurationWasSuccessful = false

    private val loadMovieResponse = MutableLiveData<LoadMovieResponse>()


    init {
        loadConfiguration()
        loadConfigurationWasSuccessful = false
    }

    private fun loadConfiguration() {
        loadUseCase.execute(useCaseCallback = object : UseCase.Callback<Configuration> {
            override fun onSuccess(data: Configuration) {
                configuration = data
                loadConfigurationWasSuccessful = true

            }

            override fun onError(type: ErrorType, msg: String) {
                Timber.e("type: $type, msg: $msg")
                loadConfigurationWasSuccessful = false

            }

            override fun onProgress() {
                Timber.i("onProgress")
            }

        }, someClass = Configuration::class.java)
    }


    fun loadMovie() {
        if (!loadConfigurationWasSuccessful) {
            loadConfiguration()
            if (!loadConfigurationWasSuccessful) {
                loadMovieWasSuccessful.value = LoadMovieResponse.Error("Configuration can't be obtained")
                return
            }
        }

        loadUseCase.execute(useCaseCallback = object : UseCase.Callback<Movie> {
            override fun onSuccess(data: Movie) {
                val url: String

                val baseUrl = configuration?.images?.secure_base_url
                val size500 = configuration?.images?.poster_sizes!![4]
                url = baseUrl + size500 + data.poster_path

                Timber.i("poster url: $url")
                loadMovieResponse.value = LoadMovieResponse.Success(
                    MovieSummary(
                        data.id,
                        data.title,
                        url,
                        data.overview
                    )
                )

            }

            override fun onError(type: ErrorType, msg: String) {
                Timber.e("type: $type, msg: $msg")
                loadMovieResponse.value = LoadMovieResponse.Error(msg)
            }

            override fun onProgress() {
                Timber.i("onProgress")
                loadMovieResponse.value = LoadMovieResponse.Progressing
            }

        }, someClass = Movie::class.java)
    }


    fun loadMovieWasSuccessful(): LiveData<LoadMovieResponse> {
        return loadMovieResponse
    }


}