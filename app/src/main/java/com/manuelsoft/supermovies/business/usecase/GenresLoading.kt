package com.manuelsoft.supermovies.business.usecase

import com.manuelsoft.supermovies.business.GenreName
import com.manuelsoft.supermovies.business.Repository
import io.reactivex.Single
import timber.log.Timber

class GenresLoading(private val repositoryRx: Repository) {

    private val genreIds : MutableMap<String, Int> = HashMap()

    fun getGenreId(genreName: GenreName) : Single<String> {
        return loadGenreIdsFromRepository().map {
            genreIds[genreName.toString()].toString()
        }.doOnError { e-> Timber.e(e) }
    }

    private fun loadGenreIdsFromRepository() : Single<MutableMap<String, Int>> {
        return repositoryRx.getGenres().map {
            it.genreList.forEach {genre ->
                genreIds[genre.name] = genre.id
            }
            return@map genreIds
        }
    }

}