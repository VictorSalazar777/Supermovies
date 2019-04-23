package com.manuelsoft.movies2.ui

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import androidx.test.core.app.ApplicationProvider
import androidx.test.rule.ActivityTestRule
import com.manuelsoft.movies2.business.usecase.*
import com.manuelsoft.movies2.data2.MovieResult
import com.manuelsoft.movies2.repository.RepositoryRxImpl
import io.reactivex.observers.TestObserver
import org.junit.Test

import org.junit.Before
import org.junit.Rule
import java.util.concurrent.CountDownLatch

class MainActivityViewModel2Test {

    private lateinit var context: Context
    private lateinit var countDownLatch: CountDownLatch
    private lateinit var moviesUiProvider: MoviesUiProvider
    private lateinit var factory : ViewModelFactory2

    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule<MovieListActivity>(MovieListActivity::class.java)

    @Before
    fun init() {
        context = ApplicationProvider.getApplicationContext()
        countDownLatch = CountDownLatch(1)
        moviesUiProvider = MoviesUiProvider(
            MoviesLoading(RepositoryRxImpl(context)),
            GenresLoading(RepositoryRxImpl(context)))
        factory = ViewModelFactory2(moviesUiProvider)
    }

    @Test
    fun getMovieListResponse() {



    }

    @Test
    fun subscribeToMovieListProvider() {
        val viewmodel = ViewModelProviders.of(activityTestRule.activity, factory).get(MainActivityViewModel2::class.java)

        val testObserver = TestObserver<MovieUiResult>()
        viewmodel.getMovies(GenreName.Action).subscribe(testObserver)

        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
    }
}