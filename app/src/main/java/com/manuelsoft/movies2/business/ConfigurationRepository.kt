package com.manuelsoft.movies2.business

import com.manuelsoft.movies2.data.Configuration


interface ConfigurationRepository {

    interface Callback {
        fun onSuccess(data: LoadDataResponse<LoadDataType, Configuration>)
        fun onError(errorData: LoadDataResponse<LoadDataErrorType, ErrorData>)
        fun onProgress(progressData: LoadDataResponse<Void, Void>)
    }

    fun loadConfiguration(callback: Callback)

}