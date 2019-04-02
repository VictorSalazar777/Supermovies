package com.manuelsoft.movies2

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.text.Layout.JUSTIFICATION_MODE_INTER_WORD
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.manuelsoft.movies2.repository.NaiveRepositoryImpl
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val factory = ViewModelFactory(NaiveRepositoryImpl(this))
        val viewModel = ViewModelProviders.of(this, factory).get(MainActivityViewModel::class.java)

        viewModel.loadMovieSuccessful().observe(this, Observer {
            txv_title.text = it.title
            val url = it.posterUrl
            Glide.with(this).load(url).into(img_movie)
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {

                overview.loadData(getString(R.string.movie_overview, it.overview), "text/html", "UTF-8")

            }
        })

        btn_load_movie.setOnClickListener {
            viewModel.loadMovie()
        }
    }
}
