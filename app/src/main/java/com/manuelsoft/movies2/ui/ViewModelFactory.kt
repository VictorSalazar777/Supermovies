package com.manuelsoft.movies2.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.manuelsoft.movies2.business.usecase.LoadUseCase

class ViewModelFactory(private val loadUseCase: LoadUseCase) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            return MainActivityViewModel(loadUseCase) as T
        }

        throw IllegalArgumentException("unknown modelClass $modelClass")
    }
}