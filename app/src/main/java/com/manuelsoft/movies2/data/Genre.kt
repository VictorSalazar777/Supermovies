package com.manuelsoft.movies2.data

import com.google.gson.annotations.SerializedName

data class Genre(
    @field:SerializedName("id")
    val id: Long,
    @field:SerializedName("name")
    val name: String)