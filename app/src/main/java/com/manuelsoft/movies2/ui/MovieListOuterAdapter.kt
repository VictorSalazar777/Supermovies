package com.manuelsoft.movies2.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.manuelsoft.movies2.MovieUi
import com.manuelsoft.movies2.R
import kotlinx.android.synthetic.main.card_view_outer_movie_list.view.*

class MovieListOuterAdapter(private val context: Context) :
    RecyclerView.Adapter<MovieListOuterAdapter.MyViewHolder>() {
    private var movieCategoriesList : List<MovieUi>? = null
    private val viewPool = RecyclerView.RecycledViewPool()

    fun setData(movieCategoriesList: List<MovieUi>?) {
        this.movieCategoriesList = movieCategoriesList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.card_view_outer_movie_list, parent, false)
        val holder = MyViewHolder(itemView)
        holder.itemView.rv_movie_list.setRecycledViewPool(viewPool)
        holder.itemView.rv_movie_list.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        return holder
    }

    override fun getItemCount(): Int {
        if (movieCategoriesList == null) {
            return 0
        }
        return movieCategoriesList!!.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movieUi = movieCategoriesList?.get(position)
        val movieListInnerAdapter = MovieListInnerAdapter(context)
        movieUi?.let {
            movieListInnerAdapter.setData(movieUi.movieInfoList)
            holder.setData("Comic", movieListInnerAdapter)
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setData(genreTitle: String, movieListInnerAdapter: MovieListInnerAdapter) {
            itemView.txv_title_category.text = genreTitle
            itemView.rv_movie_list.adapter = movieListInnerAdapter
            itemView.rv_movie_list.setHasFixedSize(true)
        }
    }
}
