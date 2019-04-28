package com.manuelsoft.movies2.business.usecase

import com.manuelsoft.movies2.business.RepositoryRx
import com.manuelsoft.movies2.data.Genre
import io.reactivex.Maybe
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

class GenresLoading(private val repositoryRx: RepositoryRx) {

    private val genreIds : MutableMap<String, Int> = HashMap()


    fun getGenreId(genreName: GenreName) : Single<String> {
        return loadGenreIds().map {
            genreIds[genreName.toString()].toString()
        }
    }

    private fun loadGenreIds(): Single<MutableMap<String, Int>> {
        return Maybe
            .concat(loadGenreIdsFromCache(), loadGenreIdsFromRepository().toMaybe())
            .firstElement()
            .toSingle()

    }

    private fun loadGenreIdsFromCache() : Maybe<MutableMap<String, Int>> {
        return Single.fromCallable {
            genreIds
        }.filter{genreIds.isNotEmpty()}
    }

    private fun loadGenreIdsFromRepository() : Single<MutableMap<String, Int>> {
        return repositoryRx.getGenres().map {
            it.forEach { genre: Genre ->
                genreIds[genre.name] = genre.id
            }
            return@map genreIds
        }
    }

    fun getGenreId2(genreName: GenreName) : Single<String> {
        return loadGenreIdsFromRepository2().map {
            genreIds[genreName.toString()].toString()
        }.doOnError { e-> Timber.e(e) }
    }

    private fun loadGenreIdsFromRepository2() : Single<MutableMap<String, Int>> {
        return repositoryRx.getGenres2().map {
            it.genreList.forEach {genre ->
                genreIds[genre.name] = genre.id
            }
            return@map genreIds
        }
    }

}