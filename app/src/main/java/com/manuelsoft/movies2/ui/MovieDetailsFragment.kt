package com.manuelsoft.movies2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.manuelsoft.movies2.R
import com.manuelsoft.movies2.repository.NaiveRepositoryImpl
import kotlinx.android.synthetic.main.movie_details.*
import kotlinx.android.synthetic.main.movie_presentation.*

class MovieDetailsFragment : Fragment() {

    companion object {
        val TAG: String = MovieDetailsFragment::class.java.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webview_overview.isVerticalScrollBarEnabled = false
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val factory = NaiveRepositoryImpl.getInstance(requireContext())?.let {
            ViewModelFactory(
                it
            )
        }
        val viewModel = ViewModelProviders.of(requireActivity(), factory).get(MainActivityViewModel::class.java)
        viewModel.loadMovieSuccessful().observe(this, Observer {
            webview_overview.loadData(getString(R.string.movie_overview, it.overview), "text/html", "UTF-8")
            txv_overview_title.visibility = View.VISIBLE
            webview_overview.visibility = View.VISIBLE
            movie_presentation_progress_bar.visibility = View.GONE
        })

        btn_load_movie.setOnClickListener {
            txv_overview_title.visibility = View.INVISIBLE
            webview_overview.visibility = View.INVISIBLE
            movie_presentation_progress_bar.visibility = View.VISIBLE
        }
    }
}