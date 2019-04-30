package com.manuelsoft.supermovies.business.usecase

import com.manuelsoft.supermovies.business.GenreName
import com.manuelsoft.supermovies.business.MovieUiResult
import io.reactivex.Single
import timber.log.Timber

class MoviesUiProvider(private val moviesLoading: MoviesLoading,
                       private val genresLoading: GenresLoading) {

    fun getMoviesResult(genreName: GenreName) : Single<MovieUiResult> {
        return genresLoading.getGenreId(genreName)
            .flatMap { moviesLoading.getMoviesResult(it) }
            .flatMap { Single.just(MovieUiResult(genreName, it)) }
            .doOnSuccess { t -> Timber.i("result--> + $t") }
            .doOnError { e->Timber.e(e) }
    }


}