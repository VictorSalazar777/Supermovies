package com.manuelsoft.movies2.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.manuelsoft.movies2.data.Configuration
import com.manuelsoft.movies2.data.Movie
import com.manuelsoft.movies2.entity.MyMovie
import com.manuelsoft.movies2.network.MovieModule
import com.manuelsoft.movies2.repository.Repository
import okhttp3.ResponseBody
import timber.log.Timber

class MainActivityViewModel(private val repository: Repository) : ViewModel() {

    private val loadConfigurationFailure = MutableLiveData<Throwable>()
    private val loadConfigurationUnsuccessful = MutableLiveData<Int>()
    private val loadMovieFailure = MutableLiveData<Throwable>()
    private val loadMovieSuccessful = MutableLiveData<MyMovie>()
    private val loadMovieUnsuccessful = MutableLiveData<Int>()
    private var configuration: Configuration? = null
    private val loadWasSuccessful = MutableLiveData<Boolean>()


    init {
        loadConfiguration()
        loadWasSuccessful.value = false
    }

    private fun loadConfiguration() {
        repository.loadConfiguration(callback = object : MovieModule.MovieCallback<Configuration> {
            override fun onFailure(throwable: Throwable) {
                Timber.e("loadConfigurationFailure()")
                Timber.e(throwable)
                loadMovieFailure.value = throwable
            }

            override fun onSuccessful(body: Configuration?, code: Int) {
                configuration = body
            }

            override fun onUnsuccessful(code: Int, errorBody: ResponseBody?) {
                Timber.e( "%s, code = $code", errorBody?.string())
                loadConfigurationUnsuccessful.value = code
            }

        })
    }

    fun loadMovie() {
        repository.loadMovie(callback = object : MovieModule.MovieCallback<Movie> {
            override fun onFailure(throwable: Throwable) {
                Timber.e("loadMovieFailure()")
                Timber.e(throwable)
                loadMovieFailure.value = throwable
                loadWasSuccessful.value = false
            }

            override fun onSuccessful(body: Movie?, code: Int) {
                body?.let { movie ->
                    var url = ""
                    configuration?.let { configuration->
                        val baseUrl = configuration.images.secure_base_url
                        val size500 = configuration.images.poster_sizes[4]
                        url = baseUrl + size500 + movie.poster_path

                    }

                    Timber.i("poster url: $url")
                    loadMovieSuccessful.value = MyMovie(
                        movie.id,
                        movie.title,
                        url,
                        movie.overview
                        )
                    loadWasSuccessful.value = true
                }
            }

            override fun onUnsuccessful(code: Int, errorBody: ResponseBody?) {
                Timber.e( "%s, code = $code", errorBody?.string())
                loadMovieUnsuccessful.value = code
                loadWasSuccessful.value = false
            }
        })
    }

    fun loadWasSuccessful(): LiveData<Boolean> {
        return loadWasSuccessful
    }

    fun loadConfigurationFailure(): LiveData<Throwable> {
        return loadConfigurationFailure
    }

    fun loadConfigurationUnsuccessful(): LiveData<Int> {
        return loadConfigurationUnsuccessful
    }

    fun loadMovieFailure(): LiveData<Throwable> {
        return loadMovieFailure
    }

    fun loadMovieSuccessful(): LiveData<MyMovie> {
        return loadMovieSuccessful
    }

    fun loadMovieUnsuccessful(): LiveData<Int> {
        return loadMovieUnsuccessful
    }

}