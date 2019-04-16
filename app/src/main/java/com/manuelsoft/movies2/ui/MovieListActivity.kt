package com.manuelsoft.movies2.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.manuelsoft.movies2.R
import com.manuelsoft.movies2.Utils.Companion.insertData
import kotlinx.android.synthetic.main.activity_movie_list.*

class MovieListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        rv_movie_categories_container.setHasFixedSize(true)
        rv_movie_categories_container.layoutManager = LinearLayoutManager(this,
            RecyclerView.VERTICAL, false)

        rv_movie_categories_container.adapter = MovieListOuterAdapter(this)

        val adapter = MovieListOuterAdapter(this)
        adapter.setData(insertData())
        rv_movie_categories_container.adapter = adapter
    }

}

