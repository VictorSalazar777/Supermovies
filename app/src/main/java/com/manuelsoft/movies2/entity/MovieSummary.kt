package com.manuelsoft.movies2.entity

data class MovieSummary(
    val id: Long,
    val title: String,
    val posterUrl: String,
    val overview: String
)