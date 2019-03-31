package com.manuelsoft.movies2.network

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.manuelsoft.movies2.data.Configuration
import com.manuelsoft.movies2.network.MovieModule.MovieCallback
import okhttp3.ResponseBody
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class MovieModuleTest {

    private lateinit var context: Context
    private lateinit var countDownLatch: CountDownLatch

    @Before
    fun init() {
        context = ApplicationProvider.getApplicationContext()
        countDownLatch = CountDownLatch(1)
    }

    @Test
    fun loadMovie() {

        var myMovie: Configuration? = null

        val callback =  object : MovieCallback<Configuration> {
            override fun onFailure(throwable: Throwable) {
                println("onFailure(): $throwable")
            }

            override fun onSuccessful(body: Configuration?, code: Int) {
                myMovie = body
                println("onSuccessful(): body = ${myMovie.toString()}")
                println("onSuccessful(): code = $code")
                countDownLatch.countDown()
            }

            override fun onUnsuccessful(code: Int, errorBody: ResponseBody?) {
                println("onUnsuccessful(): " + errorBody?.string() + "code = $code")
            }

        }
        val movieModule = MovieModule(context)
        movieModule.loadConfiguration(callback)

        countDownLatch.await(2000, TimeUnit.MILLISECONDS)

        assertThat(myMovie, notNullValue())

    }

    @Test
    fun loadConfiguration() {

        var myConfiguration: Configuration? = null

        val callback =  object : MovieCallback<Configuration> {
            override fun onFailure(throwable: Throwable) {
                println("onFailure(): $throwable")
            }

            override fun onSuccessful(body: Configuration?, code: Int) {
                myConfiguration = body
                println("onSuccessful(): body = ${myConfiguration.toString()}")
                println("onSuccessful(): code = $code")
                countDownLatch.countDown()
            }

            override fun onUnsuccessful(code: Int, errorBody: ResponseBody?) {
                println("onUnsuccessful(): " + errorBody?.string() + "code = $code")
            }

        }
        val movieModule = MovieModule(context)
        movieModule.loadConfiguration(callback)

        countDownLatch.await(2000, TimeUnit.MILLISECONDS)

        assertThat(myConfiguration, notNullValue())

    }
}