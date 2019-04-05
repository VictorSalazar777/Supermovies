package com.manuelsoft.movies2.data

import com.google.gson.annotations.SerializedName

data class Configuration(
    @field:SerializedName("images")
    val images: Images,
    @field:SerializedName("change_keys")
    val changeKeys: List<String>
)