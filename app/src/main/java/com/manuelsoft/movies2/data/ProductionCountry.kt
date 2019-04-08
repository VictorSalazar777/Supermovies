package com.manuelsoft.movies2.data

import com.google.gson.annotations.SerializedName

data class ProductionCountry(
    @field:SerializedName("iso_3166_1")
    val iso_3166_1: String,
    @field:SerializedName("name")
    val name: String
)