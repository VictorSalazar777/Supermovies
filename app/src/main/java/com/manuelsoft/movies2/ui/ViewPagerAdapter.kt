package com.manuelsoft.movies2.ui

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.manuelsoft.movies2.R

class ViewPagerAdapter(fm: FragmentManager, private val context: Context)
    : FragmentStatePagerAdapter(fm) {

    private var count = 1

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MoviePosterFragment()
            1 -> MovieDetailsFragment()
            else -> throw IllegalArgumentException("Illegal page position!!")
        }
    }

    override fun getCount(): Int {
        return count
    }

    override fun getPageWidth(position: Int): Float {
        val isPageDoubleSize = context.applicationContext.resources.getBoolean(R.bool.viewpage_double)
        return if (isPageDoubleSize) 0.5f else 1.0f
    }

    fun enableSecondFragment(yes: Boolean) {
        count = if (yes) {
            2
        } else {
            1
        }
        notifyDataSetChanged()
    }
}