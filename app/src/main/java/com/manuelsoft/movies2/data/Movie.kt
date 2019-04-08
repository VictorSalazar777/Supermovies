package com.manuelsoft.movies2.data

import com.google.gson.annotations.SerializedName

data class Movie(
    @field:SerializedName("id")
    val id: Long,
    @field:SerializedName("imdb_id")
    val imdb_id: String,
    @field:SerializedName("adult")
    val adult: Boolean,
    @field:SerializedName("backdrop_path")
    val backdrop_path: String,
    @field:SerializedName("budget")
    val budget: Int,
    @field:SerializedName("genres")
    val genres: List<Genre>,
    @field:SerializedName("homepage")
    val homepage: String,
    @field:SerializedName("original_language")
    val original_language: String,
    @field:SerializedName("original_title")
    val original_title: String,
    @field:SerializedName("overview")
    val overview: String,
    @field:SerializedName("popularity")
    val popularity: Float,
    @field:SerializedName("poster_path")
    val poster_path: String,
    @field:SerializedName("production_companies")
    val production_companies: List<ProductionCompany>,
    @field:SerializedName("production_countries")
    val production_countries: List<ProductionCountry>,
    @field:SerializedName("release_date")
    val release_date: String,
    @field:SerializedName("revenue")
    val revenue: Long,
    @field:SerializedName("runtime")
    val runtime: Int,
    @field:SerializedName("spoken_languages")
    val spoken_languages: List<SpokenLanguage>,
    @field:SerializedName("status")
    val status: String,
    @field:SerializedName("tagline")
    val tagline: String,
    @field:SerializedName("title")
    val title: String,
    @field:SerializedName("video")
    val video: Boolean,
    @field:SerializedName("vote_average")
    val vote_average: Float,
    @field:SerializedName("vote_count")
    val vote_count: Int
)