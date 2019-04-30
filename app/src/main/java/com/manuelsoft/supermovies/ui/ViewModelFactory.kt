package com.manuelsoft.supermovies.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.manuelsoft.supermovies.business.usecase.MoviesUiProvider

class ViewModelFactory(private val moviesResults: MoviesUiProvider) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            return MainActivityViewModel(moviesResults) as T
        }

        throw ClassNotFoundException("class = " + modelClass.name)
    }
}