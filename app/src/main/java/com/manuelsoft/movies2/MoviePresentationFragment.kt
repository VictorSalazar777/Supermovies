package com.manuelsoft.movies2

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.manuelsoft.movies2.repository.NaiveRepositoryImpl
import kotlinx.android.synthetic.main.movie_presentation.*

class MoviePresentationFragment : Fragment() {

    companion object {
        val TAG: String = MoviePresentationFragment::class.java.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.movie_presentation, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val factory = NaiveRepositoryImpl.getInstance(requireContext())?.let { ViewModelFactory(it) }
        val viewModel = ViewModelProviders.of(requireActivity(), factory).get(MainActivityViewModel::class.java)
        val requestListener = object: RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean
            ): Boolean {
                txv_title.visibility = View.VISIBLE
                img_movie.visibility = View.VISIBLE
                my_progress_bar.visibility = View.GONE
                return false
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                txv_title.visibility = View.VISIBLE
                img_movie.visibility = View.VISIBLE
                my_progress_bar.visibility = View.GONE
                return false
            }

        }

        viewModel.loadMovieSuccessful().observe(this, Observer {
            txv_title.text = it.title
            val url = it.posterUrl
            Glide.with(this).load(url).listener(requestListener).into(img_movie)
        })

        btn_load_movie.setOnClickListener {
            viewModel.loadMovie()
            txv_title.visibility = View.INVISIBLE
            img_movie.visibility = View.INVISIBLE
            my_progress_bar.visibility = View.VISIBLE
        }
    }
}