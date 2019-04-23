package com.manuelsoft.movies2.data

import com.google.gson.annotations.SerializedName

data class GenresResponse(
    @field:SerializedName("genres")
    val genreList : List<Genre>
)