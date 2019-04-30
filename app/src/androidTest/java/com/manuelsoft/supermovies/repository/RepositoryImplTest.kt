package com.manuelsoft.supermovies.repository

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.manuelsoft.supermovies.business.Repository
import com.manuelsoft.supermovies.data.GenresResponse
import com.manuelsoft.supermovies.data.DiscoverMoviesResult
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class RepositoryImplTest {

    private lateinit var context: Context
    private lateinit var countDownLatch: CountDownLatch
    private lateinit var repositoryRx : Repository

    @Before
    fun init() {
        context = ApplicationProvider.getApplicationContext()
        countDownLatch = CountDownLatch(1)
        repositoryRx = RepositoryImpl(context)

    }

    @Test
    fun getDiscoverMoviesRx() {
        var ok = false
        repositoryRx.getDiscoveredMovies().subscribeOn(Schedulers.io()).subscribe(object  : SingleObserver<DiscoverMoviesResult> {
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

        repositoryRx.getGenres().subscribe(object : SingleObserver<GenresResponse> {
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
        repositoryRx.getGenres().subscribe(object : SingleObserver<GenresResponse> {
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