package com.manuelsoft.movies2.network

import com.manuelsoft.movies2.data.Configuration
import com.manuelsoft.movies2.data.Genre
import com.manuelsoft.movies2.data.GenresResponse
import com.manuelsoft.movies2.data.Movie
import com.manuelsoft.movies2.data2.DiscoverMoviesResult
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {

    @GET("movie/{movieId}")
    fun getMovie(@Path("movieId") movieId: String, @Query("api_key") apiKey: String): Call<Movie>

    @GET("configuration")
    fun getConfiguration(@Query("api_key") apiKey: String): Call<Configuration>

    @GET("genre/movie/list")
    fun getGenres(@Query("api_key") apiKey: String): Single<List<Genre>>

    @GET("genre/movie/list")
    fun getGenres2(@Query("api_key") apiKey: String): Single<GenresResponse>

    @GET("configuration")
    fun getConfigurationRx(@Query("api_key") apiKey: String): Single<Configuration>

    @GET("discover/movie?include_adult=false&include_video=false&page=1&language=en-US&sort_by=popularity.desc")
    fun discoverMovies(@Query("api_key") apiKey: String) : Single<DiscoverMoviesResult>

    @GET("discover/movie?include_adult=false&include_video=false&page=1&language=en-US&sort_by=popularity.desc")
    fun discoverMovies(@Query("api_key") apiKey: String, @Query("with_genres") genre:String) : Single<DiscoverMoviesResult>

}