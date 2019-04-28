package com.manuelsoft.movies2.ui

import androidx.lifecycle.*
import com.manuelsoft.movies2.business.usecase.GenreName
import com.manuelsoft.movies2.business.usecase.MovieUiResult
import com.manuelsoft.movies2.business.usecase.MoviesUiProvider
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

//sealed class MovieListResponse {
//    data class Success(val movieUiResult: List<MovieResult>) : MovieListResponse()
//    data class Error(val msg: String) : MovieListResponse()
//}

sealed class MovieResponse {
    data class Success(val movieUiResult: MovieUiResult) : MovieResponse()
    data class Error(val msg: String, val genreName: GenreName) : MovieResponse()
}

class MainActivityViewModel2(
    private val moviesResults: MoviesUiProvider
) : ViewModel(), LifecycleObserver {

    private val compositeDisposable = CompositeDisposable()

    private val movieResponseListLiveData = MutableLiveData<List<MovieResponse>>()
    private val movieResponseList = ArrayList<MovieResponse>()

    init {
        subscribeToMovieListProvider()
    }

    fun getMovieResponseListLiveData(): LiveData<List<MovieResponse>> {
        return movieResponseListLiveData
    }

    private fun setMovieResponseListLiveData() {
        movieResponseListLiveData.postValue(movieResponseList)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun subscribeToMovieListProvider() {
        Timber.i("subscribeToMovieListProvider()")

        val genres = listOf(
            GenreName.Action, GenreName.History, GenreName.Music,
            GenreName.Fantasy, GenreName.Family, GenreName.Western
        )


        Observable.fromIterable(genres)
            .subscribeOn(Schedulers.io())
            .flatMapSingle(::getMovies)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<MovieUiResult> {
                override fun onComplete() {
                    setMovieResponseListLiveData()
                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onNext(t: MovieUiResult) {
                    val response = MovieResponse.Success(t)
                    movieResponseList.add(response)
                }

                override fun onError(e: Throwable) {
                }

            })

    }

    fun getMovies(genreName: GenreName): Single<MovieUiResult> {
        return moviesResults
            .getMoviesResult2(genreName)
//            .doOnSuccess { t -> Timber.i("result--> + $t") }
            .doOnError {
                Timber.e(it)
                val response = MovieResponse.Error("Some error ocurred", genreName)
                movieResponseList.add(response)
            }
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun disposeAll() {
        compositeDisposable.clear()
    }


}