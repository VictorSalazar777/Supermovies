package com.manuelsoft.supermovies.data

import com.google.gson.annotations.SerializedName

data class GenresResponse(
    @field:SerializedName("genres")
    val genreList : List<Genre>
)