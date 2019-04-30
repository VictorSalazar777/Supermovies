package com.manuelsoft.movies2.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.manuelsoft.movies2.business.usecase.MoviesUiProvider

class ViewModelFactory(private val moviesResults: MoviesUiProvider) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel2::class.java)) {
            return MainActivityViewModel2(moviesResults) as T
        }

        throw ClassNotFoundException("class = " + modelClass.name)
    }
}