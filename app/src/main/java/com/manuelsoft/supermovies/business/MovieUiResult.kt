package com.manuelsoft.supermovies.business

import com.manuelsoft.supermovies.data.MovieResult

data class MovieUiResult(
    val genreName: GenreName,
    val movieResults: List<MovieResult>
)