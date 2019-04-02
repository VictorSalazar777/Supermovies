package com.manuelsoft.movies2

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.manuelsoft.movies2.repository.NaiveRepositoryImpl
import kotlinx.android.synthetic.main.movie_presentation.*

class MoviePresentationFragment : Fragment() {

    companion object {
        val TAG = MoviePresentationFragment::class.java.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.movie_presentation, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val factory = ViewModelFactory(NaiveRepositoryImpl(requireContext()))
        val viewModel = ViewModelProviders.of(requireActivity(), factory).get(MainActivityViewModel::class.java)

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