package com.manuelsoft.movies2.business

import com.manuelsoft.movies2.business.usecase.ErrorType

interface Repository {

    interface Callback<T> {
        fun onSuccess(t: T?)
        fun onError(type: ErrorType, msg: String, extra: String?)
        fun onProgress()
    }

    fun <T> load(repositoryCallback: Callback<T>, someClass: Class<T>)
}