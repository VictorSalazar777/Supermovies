package com.manuelsoft.movies2.business.usecase

import com.manuelsoft.movies2.business.Repository
import io.reactivex.Single
import timber.log.Timber

enum class GenreName {
    Action, Adventure, Animation, Comedy, Crime, Drama,
    Family, Fantasy, History, Horror, Music, Mystery, Romance,
    Documentary,
    ScienceFiction {

        override fun toString(): String {
            return "Science Fiction"
        }
    },
    TVMovie {
        override fun toString(): String {
            return "TV Movie"
        }
    }, Thriller, War, Western
}

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