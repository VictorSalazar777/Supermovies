package com.manuelsoft.movies2.data

import com.google.gson.annotations.SerializedName

data class SpokenLanguage(
    @field:SerializedName("iso_639_1")
    val iso_639_1: String,
    @field:SerializedName("name")
    val name: String
)