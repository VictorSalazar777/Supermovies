package com.manuelsoft.movies2.ui

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.manuelsoft.movies2.R
import com.manuelsoft.movies2.repository.NaiveRepositoryImpl
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, this)
        viewpager.adapter = viewPagerAdapter
        viewpager.currentItem = 0

        val factory = NaiveRepositoryImpl.getInstance(this)?.let { ViewModelFactory(it) }
        val viewModel = ViewModelProviders.of(this, factory).get(MainActivityViewModel::class.java)
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            viewModel.loadWasSuccessful().observe(this, Observer {
                viewPagerAdapter.enableSecondFragment(it)
            })
        } else {
            viewPagerAdapter.enableSecondFragment(true)
        }

    }
}