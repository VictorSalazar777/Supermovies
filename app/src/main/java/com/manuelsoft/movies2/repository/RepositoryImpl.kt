package com.manuelsoft.movies2.repository

import android.content.Context
import com.manuelsoft.movies2.business.Repository
import com.manuelsoft.movies2.business.usecase.ErrorType
import com.manuelsoft.movies2.network.RetrofitProvider
import okhttp3.ResponseBody
import timber.log.Timber

class RepositoryImpl private constructor(context: Context) : Repository {

    companion object {
        private var instance: RepositoryImpl? = null
        private var LOCK = Object()

        fun getInstance(context: Context): Repository? {
            if (instance == null) {
                synchronized(LOCK) {
                    if (instance == null) {
                        instance = RepositoryImpl(context = context.applicationContext)
                    }
                }
            }

            return instance
        }
    }

    private val retrofit = RetrofitProvider(context)

    override fun <T> load(repositoryCallback: Repository.Callback<T>, someClass: Class<T>) {

        repositoryCallback.onProgress()

        retrofit.loadData(retrofitProviderCallback = object : RetrofitProvider.Callback<T> {
            override fun onFailure(throwable: Throwable) {
                throwable.printStackTrace()
                repositoryCallback.onError(ErrorType.FAILURE, throwable.toString(), null)
            }

            override fun onSuccessful(body: T?) {
                Timber.i("onSuccessful(), body: $body")
                repositoryCallback.onSuccess(body)
            }

            override fun onUnsuccessful(code: Int, errorBody: ResponseBody?) {
                Timber.i("onUnSuccessful(), code $code, error: ${errorBody?.string()}")
                repositoryCallback.onError(ErrorType.UNSUCCESSFUL, code.toString(), errorBody?.string())
            }

        }, someClass = someClass)

    }


}