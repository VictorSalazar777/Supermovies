package com.manuelsoft.movies2

import android.content.Context
import android.util.DisplayMetrics

data class MovieInfo(val id: Long, val title: String, val imageUrl: String)
data class MovieUi(val genre: Int, val movieInfoList: List<MovieInfo>)


class Utils {
    companion object {
        fun getRandomDoubleBetweenRange(min: Double, max: Double): Double {
            return Math.random() * (max - min + 1) + min
        }

        fun convertDpToPixel(dp: Float, context: Context): Float {
            return dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
        }

        fun insertData() : List<MovieUi>? {
            val list = ArrayList<MovieUi>(5)
            for(i in 1..5) {
                val genre = 1
                val movieInfoList = ArrayList<MovieInfo>(10)

                for (j in 1..10) {
                    val id = 1L
                    val title = "Scary Movie"
                    val imageUrl = "https://image.tmdb.org/t/p/w300/8juTRqn5o43mnlVacp1IzZSd11N.jpg"
                    val movieInfo = MovieInfo(id, title, imageUrl)
                    movieInfoList.add(movieInfo)
                }

                val movieUi = MovieUi(genre, movieInfoList)
                list.add(movieUi)
            }
            return list
        }


    }
}