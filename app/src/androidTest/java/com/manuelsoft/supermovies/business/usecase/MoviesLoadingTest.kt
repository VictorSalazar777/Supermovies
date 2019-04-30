package com.manuelsoft.supermovies.business.usecase

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.manuelsoft.supermovies.data.MovieResult
import com.manuelsoft.supermovies.repository.RepositoryImpl
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class MoviesLoadingTest {

    private lateinit var context: Context
    private lateinit var countDownLatch: CountDownLatch
    private lateinit var summaryMoviesLoading: MoviesLoading

    @Before
    fun init() {
        context = ApplicationProvider.getApplicationContext()
        countDownLatch = CountDownLatch(1)
        summaryMoviesLoading = MoviesLoading(RepositoryImpl(context))

    }

    @Test
    fun getMoviesResult() {
        val testObserver = TestObserver<List<MovieResult>>()
        summaryMoviesLoading.getMoviesResult().subscribe(testObserver)
        countDownLatch.countDown()

        countDownLatch.await(3, TimeUnit.SECONDS)
        testObserver.assertNoErrors()
        testObserver.assertComplete()
        testObserver.assertValueCount(1)
        testObserver.assertTerminated()
    }

}