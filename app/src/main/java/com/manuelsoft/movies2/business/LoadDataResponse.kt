package com.manuelsoft.movies2.business

enum class LoadDataType {
    NETWORK,
    CACHE
}

enum class LoadDataErrorType {
    FAILURE,
    UNSUCCESSFUL
}

data class ErrorData(val msg: String?, val httpErrorCode: Int?)

data class LoadDataResponse<out Type, out Data>(val type: Type?, val data: Data?) {
    companion object {
        fun <Data> onResponse(type: LoadDataType?, data: Data?): LoadDataResponse<LoadDataType, Data> {
            return LoadDataResponse(type, data)
        }

        fun onError(type: LoadDataErrorType?, errorData: ErrorData?): LoadDataResponse<LoadDataErrorType, ErrorData> {
            return LoadDataResponse(type, errorData)
        }

        fun onProgress(): LoadDataResponse<Void, Void> {
            return LoadDataResponse(null, null)
        }
    }
}