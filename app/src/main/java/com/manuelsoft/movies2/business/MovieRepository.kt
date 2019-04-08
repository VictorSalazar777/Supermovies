package com.manuelsoft.movies2.business

import com.manuelsoft.movies2.data.Movie

interface MovieRepository {
    interface Callback {
        fun onSuccess(data: LoadDataResponse<LoadDataType, Movie>)
        fun onError(errorData: LoadDataResponse<LoadDataErrorType, ErrorData>)
        fun onProgress(progressData: LoadDataResponse<Void, Void>)
    }

    fun loadMovie(callback: MovieRepository.Callback)

}