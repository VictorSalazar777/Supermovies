package com.manuelsoft.supermovies.ui

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import androidx.test.core.app.ApplicationProvider
import androidx.test.rule.ActivityTestRule
import com.manuelsoft.supermovies.business.GenreName
import com.manuelsoft.supermovies.business.MovieUiResult
import com.manuelsoft.supermovies.business.usecase.*
import com.manuelsoft.supermovies.repository.RepositoryImpl
import io.reactivex.observers.TestObserver
import org.junit.Test

import org.junit.Before
import org.junit.Rule
import java.util.concurrent.CountDownLatch

class MainActivityViewModelTest {

    private lateinit var context: Context
    private lateinit var countDownLatch: CountDownLatch
    private lateinit var moviesUiProvider: MoviesUiProvider
    private lateinit var factory : ViewModelFactory

    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule<MovieListActivity>(MovieListActivity::class.java)

    @Before
    fun init() {
        context = ApplicationProvider.getApplicationContext()
        countDownLatch = CountDownLatch(1)
        moviesUiProvider = MoviesUiProvider(
            MoviesLoading(RepositoryImpl(context)),
            GenresLoading(RepositoryImpl(context)))
        factory = ViewModelFactory(moviesUiProvider)
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