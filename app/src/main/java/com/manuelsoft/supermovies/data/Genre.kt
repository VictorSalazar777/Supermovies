package com.manuelsoft.supermovies.data

import com.google.gson.annotations.SerializedName

data class Genre(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("name")
    val name: String
)