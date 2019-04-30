package com.manuelsoft.supermovies.network

import android.content.Context
import com.manuelsoft.supermovies.R
import com.manuelsoft.supermovies.SingletonHolder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitProvider private constructor(context: Context){

    companion object : SingletonHolder<RetrofitProvider, Context>(::RetrofitProvider)

    private val retrofit : Retrofit by lazy {
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


}