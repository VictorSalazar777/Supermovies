package com.manuelsoft.movies2

class Utils {
    companion object {
        fun getRandomDoubleBetweenRange(min: Double, max: Double): Double {
            return Math.random() * (max - min + 1) + min
        }
    }
}