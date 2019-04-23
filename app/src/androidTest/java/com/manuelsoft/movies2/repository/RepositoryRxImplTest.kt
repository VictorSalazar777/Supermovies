package com.manuelsoft.movies2.repository

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.manuelsoft.movies2.business.RepositoryRx
import com.manuelsoft.movies2.data.Configuration
import com.manuelsoft.movies2.data.GenresResponse
import com.manuelsoft.movies2.data2.DiscoverMoviesResult
import io.reactivex.Scheduler
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class RepositoryRxImplTest {

    private lateinit var context: Context
    private lateinit var countDownLatch: CountDownLatch
    private lateinit var repositoryRx : RepositoryRx


    @Before
    fun init() {
        context = ApplicationProvider.getApplicationContext()
        countDownLatch = CountDownLatch(1)
        repositoryRx = RepositoryRxImpl(context)

    }

    @Test
    fun getConfigurationFromNetwork() {
        val repositoryRx = RepositoryRxImpl(context)
        var ok = false
        repositoryRx.getConfigurationFromNetwork().subscribe(object : SingleObserver<Configuration> {
            override fun onSubscribe(d: Disposable) {
            }

            override fun onSuccess(t: Configuration) {
                println(t)
                ok = true
                countDownLatch.countDown()
            }

            override fun onError(e: Throwable) {
                println(e)
                e.printStackTrace()
            }

        })

        countDownLatch.await(3, TimeUnit.SECONDS)
        assertTrue(ok)
    }

    @Test
    fun getConfigurationRx() {
        var ok = false
        repositoryRx.getConfigurationRx().subscribe(object : SingleObserver<Configuration> {
            override fun onSubscribe(d: Disposable) {
            }

            override fun onSuccess(t: Configuration) {
                println(t)
                ok = true
                countDownLatch.countDown()
            }

            override fun onError(e: Throwable) {
                println(e)
                e.printStackTrace()
            }

        })

        countDownLatch.await(3, TimeUnit.SECONDS)
        assertTrue(ok)


        ok = false
        repositoryRx.getConfigurationRx().subscribe(object : SingleObserver<Configuration> {
            override fun onSubscribe(d: Disposable) {
            }

            override fun onSuccess(t: Configuration) {
                println(t)
                ok = true
                countDownLatch.countDown()
            }

            override fun onError(e: Throwable) {
                println(e)
                e.printStackTrace()
            }

        })

        countDownLatch.await(3, TimeUnit.SECONDS)
        assertTrue(ok)
    }

    @Test
    fun getDiscoverMoviesRx() {
        var ok = false
        repositoryRx.getDiscoveredMoviesRx().subscribeOn(Schedulers.io()).subscribe(object  : SingleObserver<DiscoverMoviesResult> {
            override fun onSuccess(t: DiscoverMoviesResult) {
                println("thread = " + Thread.currentThread().name)
                println("size = " + t.movieListResult.size)
                ok = true
                countDownLatch.countDown()
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onError(e: Throwable) {
            }

        })

        countDownLatch.await(3, TimeUnit.SECONDS)
        assertTrue(ok)

    }

    @Test
    fun getGenres2() {
        var ok = false

        repositoryRx.getGenres2().subscribe(object : SingleObserver<GenresResponse> {
            override fun onSuccess(t: GenresResponse) {
                ok = true
                countDownLatch.countDown()
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onError(e: Throwable) {
                println("test1 -> $e")

            }

        })

        countDownLatch.await(3, TimeUnit.SECONDS)
        assertTrue(ok)


        ok = false
        repositoryRx.getGenres2().subscribe(object : SingleObserver<GenresResponse> {
            override fun onSuccess(t: GenresResponse) {
                ok = true
                countDownLatch.countDown()
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onError(e: Throwable) {
                println("test2 -> $e")
            }

        })

        countDownLatch.await(3, TimeUnit.SECONDS)
        assertTrue(ok)
    }
}