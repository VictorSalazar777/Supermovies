package com.manuelsoft.movies2.data

import com.google.gson.annotations.SerializedName

data class Images(
    @field:SerializedName("images")
    val base_url: String,
    @field:SerializedName("secure_base_url")
    val secure_base_url: String,
    @field:SerializedName("backdrop_sizes")
    val backdrop_sizes: List<String>,
    @field:SerializedName("logo_sizes")
    val logo_sizes: List<String>,
    @field:SerializedName("poster_sizes")
    val poster_sizes: List<String>,
    @field:SerializedName("profile_sizes")
    val profile_sizes: List<String>,
    @field:SerializedName("still_sizes")
    val still_sizes: List<String>
)