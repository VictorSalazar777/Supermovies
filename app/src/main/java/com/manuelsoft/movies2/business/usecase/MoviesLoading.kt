package com.manuelsoft.movies2.business.usecase

import com.manuelsoft.movies2.business.RepositoryRx
import com.manuelsoft.movies2.data.Configuration
import com.manuelsoft.movies2.data2.MovieResult
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import timber.log.Timber

class MoviesLoading(private val repositoryRx: RepositoryRx) {

    private lateinit var url : String

    fun getConfiguration() {
        repositoryRx.getConfigurationRx().subscribe(object : SingleObserver<Configuration>{
            override fun onSuccess(t: Configuration) {
                url = t.images.secure_base_url
                Timber.i("url = $url")
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onError(e: Throwable) {
                Timber.e(e)
            }

        })
    }

    fun getMoviesResult() : Single<List<MovieResult>> {
        return repositoryRx.getDiscoveredMoviesRx().map{
            it.movieListResult
        }
    }

    fun getMoviesResult(genre: String) : Single<List<MovieResult>> {
        return repositoryRx.getDiscoveredMoviesRx(genre).map{
            it.movieListResult
        }.doOnSuccess {
            Timber.i("result--> $it")
        }
    }


}