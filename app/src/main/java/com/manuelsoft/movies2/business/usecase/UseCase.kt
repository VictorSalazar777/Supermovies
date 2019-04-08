package com.manuelsoft.movies2.business.usecase


enum class ErrorType {
    FAILURE,
    UNSUCCESSFUL,
    UNKNOWN
}

abstract class UseCase {

    interface Callback<T> {
        fun onSuccess(data: T)
        fun onError(type: ErrorType, msg: String)
        fun onProgress()
    }

    protected abstract fun <T> onExecute(useCaseCallback: Callback<T>, someClass: Class<T>)

    fun <T> execute(useCaseCallback: Callback<T>, someClass: Class<T>) {
        onExecute(useCaseCallback, someClass)
    }
}