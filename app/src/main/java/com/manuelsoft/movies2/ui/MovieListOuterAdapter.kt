package com.manuelsoft.movies2.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.manuelsoft.movies2.R
import kotlinx.android.synthetic.main.card_view_outer_movie_list.view.*

class MovieListOuterAdapter(private val context: Context) :
    RecyclerView.Adapter<MovieListOuterAdapter.MyViewHolder>() {
    private var movieResponseList : List<MovieResponse>? = null
    private val viewPool = RecyclerView.RecycledViewPool()

    fun setData(movieResponseList: List<MovieResponse>) {
        this.movieResponseList = movieResponseList
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
        if (movieResponseList == null) {
            return 0
        }
        return movieResponseList!!.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movieResponse = movieResponseList?.get(position)
        val movieListInnerAdapter = MovieListInnerAdapter(context)
        movieResponse?.let {
            when (it) {
                is MovieResponse.Success -> {
                    movieListInnerAdapter.setData(it.movieUiResult.genreName, it.movieUiResult.movieResults)
                    holder.setData(it.movieUiResult.genreName.toString(), movieListInnerAdapter)
                }

                is MovieResponse.Error -> {
                    movieListInnerAdapter.setData(it.genreName,null)
                    holder.setData(it.genreName.name, movieListInnerAdapter)
                }
            }
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
