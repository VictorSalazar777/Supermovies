package com.manuelsoft.movies2.business.usecase

import com.manuelsoft.movies2.business.Repository
import timber.log.Timber


class LoadUseCase(private val repository: Repository) : UseCase() {

    override fun <T> onExecute(useCaseCallback: UseCase.Callback<T>, someClass: Class<T>) {
        repository.load(repositoryCallback = object : Repository.Callback<T> {

            override fun onSuccess(t: T?) {
                Timber.i("onSuccessful(), body: $t")
                if (t == null) {
                    onError(ErrorType.UNKNOWN, "Null", null)
                    return
                }

                useCaseCallback.onSuccess(t)
            }

            override fun onError(type: ErrorType, msg: String, extra: String?) {
                Timber.i("onError()")

                if (type == ErrorType.UNSUCCESSFUL) {
                    useCaseCallback.onError(type, "code = $msg, $extra")
                }
                useCaseCallback.onError(type, msg)

            }

            override fun onProgress() {
                Timber.i("onProgress()")
                useCaseCallback.onProgress()
            }

        }, someClass = someClass)
    }
}