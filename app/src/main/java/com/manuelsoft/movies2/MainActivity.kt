package com.manuelsoft.movies2

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    //private val fragmentTagsMap = HashMap<String, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val moviePresentationFragment = MoviePresentationFragment()
       // fragmentTagsMap.put(MoviePresentationFragment.TAG, moviePresentationFragment.toString())
        supportFragmentManager.beginTransaction().add(R.id.movie_presentation_container,
            moviePresentationFragment, moviePresentationFragment.toString()).commit()

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            val movieDetailsFragment = MovieDetailsFragment()
            supportFragmentManager.beginTransaction().add(R.id.movie_details_container,
                movieDetailsFragment, movieDetailsFragment.toString()).commit()
        }

    }
}