package com.manuelsoft.movies2

import com.manuelsoft.movies2.data.Movie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("3/movie/550")
    fun getMovie(@Query("api_key") apiKey: String): Call<Movie>

    @GET("xyz")
    fun getNothing(@Query("api_key") apiKey: String): Call<Movie>

}