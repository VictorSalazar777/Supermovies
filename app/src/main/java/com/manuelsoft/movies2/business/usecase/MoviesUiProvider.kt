package com.manuelsoft.movies2.business.usecase

import com.manuelsoft.movies2.data2.MovieResult
import io.reactivex.Single
import timber.log.Timber

data class MovieUiResult(
    val genreName: GenreName,
    val movieResults: List<MovieResult>
)

class MoviesUiProvider(private val moviesLoading: MoviesLoading,
                       private val genresLoading: GenresLoading) {

    fun getMoviesResult(genreName: GenreName) : Single<MovieUiResult> {
        return genresLoading.getGenreId(genreName)
            .flatMap { moviesLoading.getMoviesResult(it) }
            .flatMap { Single.just(MovieUiResult(genreName, it))
        }.doOnError { e->Timber.e(e) }
    }

    fun getMoviesResult2(genreName: GenreName) : Single<MovieUiResult> {
        return genresLoading.getGenreId2(genreName)
            .flatMap { moviesLoading.getMoviesResult(it) }
            .flatMap { Single.just(MovieUiResult(genreName, it)) }
            .doOnSuccess { t -> Timber.i("result--> + $t") }
            .doOnError { e->Timber.e(e) }
    }


}