package com.manuelsoft.movies2.network

import com.manuelsoft.movies2.data.Configuration
import com.manuelsoft.movies2.data.Movie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/{movieId}")
    fun getMovie(@Path("movieId") movieId: String, @Query("api_key") apiKey: String): Call<Movie>

    @GET("configuration")
    fun getConfiguration(@Query("api_key") apiKey: String): Call<Configuration>


}