package com.manuelsoft.movies2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.manuelsoft.movies2.repository.NaiveRepositoryImpl
import kotlinx.android.synthetic.main.movie_details.*

class MovieDetailsFragment : Fragment() {

    companion object {
        val TAG = MovieDetailsFragment::class.java.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.movie_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val factory = ViewModelFactory(NaiveRepositoryImpl(requireContext()))
        val viewModel = ViewModelProviders.of(requireActivity(), factory).get(MainActivityViewModel::class.java)
        viewModel.loadMovieSuccessful().observe(this, Observer {
            overview.loadData(getString(R.string.movie_overview, it.overview), "text/html", "UTF-8")

        })
    }
}