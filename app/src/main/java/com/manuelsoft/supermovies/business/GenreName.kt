package com.manuelsoft.supermovies.business

enum class GenreName {
    Action, Adventure, Animation, Comedy, Crime, Drama,
    Family, Fantasy, History, Horror, Music, Mystery, Romance,
    Documentary,
    ScienceFiction {

        override fun toString(): String {
            return "Science Fiction"
        }
    },
    TVMovie {
        override fun toString(): String {
            return "TV Movie"
        }
    }, Thriller, War, Western
}