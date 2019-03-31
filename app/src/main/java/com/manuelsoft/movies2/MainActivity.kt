package com.manuelsoft.movies2

import android.os.Bundle
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
        })

        btn_load_movie.setOnClickListener {
            viewModel.loadMovie()
        }
    }
}
