package com.manuelsoft.supermovies.network

import com.manuelsoft.supermovies.data.GenresResponse
import com.manuelsoft.supermovies.data.DiscoverMoviesResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("genre/movie/list")
    fun getGenres(@Query("api_key") apiKey: String): Single<GenresResponse>

    @GET("discover/movie?include_adult=false&include_video=false&page=1&language=en-US&sort_by=popularity.desc")
    fun discoverMovies(@Query("api_key") apiKey: String) : Single<DiscoverMoviesResult>

    @GET("discover/movie?include_adult=false&include_video=false&page=1&language=en-US&sort_by=popularity.desc")
    fun discoverMovies(@Query("api_key") apiKey: String, @Query("with_genres") genre:String) : Single<DiscoverMoviesResult>

}