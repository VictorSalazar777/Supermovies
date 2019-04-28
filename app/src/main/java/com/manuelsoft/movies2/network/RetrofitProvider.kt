package com.manuelsoft.movies2.network

import android.content.Context
import com.manuelsoft.movies2.R
import com.manuelsoft.movies2.SingletonHolder
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitProvider private constructor(context: Context){

    companion object : SingletonHolder<RetrofitProvider, Context>(::RetrofitProvider)

    private val retrofit : Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(context.getString(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val retrofitRx : Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(context.getString(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          //  .client(ApiWorker.client)
            .build()
    }

    fun getRetrofitService() : RetrofitService{
        return retrofit.create(RetrofitService::class.java)
    }

    fun getRetrofitServiceRx() : RetrofitService{
        return retrofitRx.create(RetrofitService::class.java)
    }

    interface Callback<T> {
        fun onFailure(throwable: Throwable)
        fun onSuccessful(body: T?)
        fun onUnsuccessful(code: Int, errorBody: ResponseBody?)
    }



}