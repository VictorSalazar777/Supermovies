package com.manuelsoft.movies2.repository

import android.content.Context
import com.manuelsoft.movies2.business.*
import com.manuelsoft.movies2.data.Configuration
import com.manuelsoft.movies2.network.RetrofitLoads
import com.manuelsoft.movies2.network.RetrofitProvider
import okhttp3.ResponseBody

class ConfigurationRepositoryImpl private constructor(context: Context) : ConfigurationRepository {

    companion object {
        private var instance: ConfigurationRepositoryImpl? = null
        private var LOCK = Object()
        fun getInstance(context: Context): ConfigurationRepository? {
            if (instance == null) {
                synchronized(LOCK) {
                    if (instance == null) {
                        instance = ConfigurationRepositoryImpl(context = context.applicationContext)
                    }
                }
            }

            return instance
        }
    }

    private val retrofitLoads = RetrofitLoads(context)


    override fun loadConfiguration(callback: ConfigurationRepository.Callback) {
        callback.onProgress(LoadDataResponse.onProgress())
        retrofitLoads.loadConfiguration(callback = object : RetrofitProvider.Callback<Configuration> {
            override fun onFailure(throwable: Throwable) {
                callback.onError(
                    LoadDataResponse.onError(
                        LoadDataErrorType.FAILURE,
                        ErrorData(throwable.toString(), null)
                    )
                )
                throwable.printStackTrace()
            }

            override fun onSuccessful(body: Configuration?) {
                callback.onSuccess(LoadDataResponse.onResponse(LoadDataType.NETWORK, body))
            }

            override fun onUnsuccessful(code: Int, errorBody: ResponseBody?) {
                callback.onError(
                    LoadDataResponse.onError(
                        LoadDataErrorType.UNSUCCESSFUL,
                        ErrorData(errorBody?.string(), code)
                    )
                )
            }

        })
    }

}