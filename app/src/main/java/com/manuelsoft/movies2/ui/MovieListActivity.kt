package com.manuelsoft.movies2.ui

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.manuelsoft.movies2.business.usecase.GenresLoading
import com.manuelsoft.movies2.business.usecase.MoviesLoading
import com.manuelsoft.movies2.business.usecase.MoviesUiProvider
import com.manuelsoft.movies2.repository.RepositoryImpl
import kotlinx.android.synthetic.main.activity_movie_list.*


class MovieListActivity : AppCompatActivity() {

    private val adapter: MovieListOuterAdapter by lazy {
        MovieListOuterAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.manuelsoft.movies2.R.layout.activity_movie_list)
        setupOuterRecyclerView()
        setupViewModelObserver()
    }

    private fun setupViewModelObserver() {
        val factory = ViewModelFactory(MoviesUiProvider(MoviesLoading(RepositoryImpl(this)),
            GenresLoading(RepositoryImpl(this))))
        val viewmodel = ViewModelProviders.of(this, factory).get(MainActivityViewModel2::class.java)
        val lifecycle = LifecycleRegistry(this)
        lifecycle.addObserver(viewmodel)
        viewmodel.getMovieResponseListLiveData().observe(this, Observer { movieResponseList: List<MovieResponse> ->
            movieResponseList.forEach { movieResponse ->
                when (movieResponse) {
                    is MovieResponse.Success -> {
//                        val movieUiList = LinkedList<MovieUi>()
//
//                        movieResponse.movieUiResult.movieResults.forEach {
//                            val movieInfoList = LinkedList<MovieInfo>()
//                                val movieInfo =
//                                    MovieInfo(it.id, it.title, "https://image.tmdb.org/t/p/w300" + it.poster_path)
//                                movieInfoList.add(movieInfo)
//
//                            val movieUi = MovieUi(i, movieInfoList)
//                            movieUiList.add(movieUi)
//                        }

                    }
                    is MovieResponse.Error -> {
                        val ssb = SpannableStringBuilder().append(movieResponse.msg)
                        ssb.setSpan(
                            ForegroundColorSpan(
                                ContextCompat.getColor(
                                    this,
                                    com.manuelsoft.movies2.R.color.colorPrimary
                                )
                            ),
                            0,
                            movieResponse.msg.length,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                        )

                        Snackbar.make(
                            findViewById(com.manuelsoft.movies2.R.id.activity_movie_list),
                            ssb,
                            Snackbar.LENGTH_SHORT
                        ).show()
                        return@forEach
                    }
                }
            }
            adapter.setData(movieResponseList)
        })

    }

    private fun setupOuterRecyclerView() {
        rv_movie_categories_container.setHasFixedSize(true)
        rv_movie_categories_container.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_movie_categories_container.adapter = MovieListOuterAdapter(this)

        // adapter.setData(insertData())
        rv_movie_categories_container.adapter = adapter
    }

}

