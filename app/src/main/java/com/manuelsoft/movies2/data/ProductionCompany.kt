package com.manuelsoft.movies2.data

import com.google.gson.annotations.SerializedName

data class ProductionCompany(
    @field:SerializedName("id")
    val id: Long,
    @field:SerializedName("logoPath")
    val logoPath: String,
    @field:SerializedName("originCountry")
    val originCountry: String)