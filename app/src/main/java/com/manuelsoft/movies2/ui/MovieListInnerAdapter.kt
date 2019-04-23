package com.manuelsoft.movies2.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.manuelsoft.movies2.R
import com.manuelsoft.movies2.business.usecase.GenreName
import com.manuelsoft.movies2.data2.MovieResult
import com.manuelsoft.movies2.glide.GlideApp
import kotlinx.android.synthetic.main.card_view_inner_movie_list.view.*


class MovieListInnerAdapter(private val context: Context) : RecyclerView.Adapter<MovieListInnerAdapter.MyViewHolder>() {

    private var movieResultList: List<MovieResult>? = null
    private var requestListener :RequestListener<Drawable>
    private lateinit var progressBar : ProgressBar

    init {

        requestListener = object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                progressBar.visibility = View.GONE
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                progressBar.visibility = View.GONE
                return false
            }

        }
    }

    fun setData(genreName: GenreName, movieResultList: List<MovieResult>?) {
        this.movieResultList = movieResultList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.card_view_inner_movie_list, parent, false)
        progressBar = itemView.findViewById(R.id.movie_progress_bar)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        if (movieResultList == null) {
            return 0
        }
        return movieResultList!!.size
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movieResult = movieResultList?.get(position)
        val url = "https://image.tmdb.org/t/p/w300" + movieResult?.poster_path
        bindImage(movieResult, url, holder)

    }

    private fun bindImage(movieResult: MovieResult?, url: String?, holder: MyViewHolder) {
    //    holder.itemView.iv_movie_image.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.placeholder))
        if (movieResult != null) {
            progressBar.visibility = View.VISIBLE
            GlideApp.with(context)
                .load(url)
                .error(R.drawable.placeholder)
                .listener(requestListener)
                .priority(Priority.HIGH)
                .into(holder.itemView.iv_movie_image)
            holder.setData(movieResult.id, movieResult.title)
        } else {
            GlideApp.with(context).clear(holder.itemView.iv_movie_image)
            holder.itemView.iv_movie_image.setImageDrawable(null)
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var movieId : Long? = null
        fun setData(movieId : Long, title : String) {
            this.movieId = movieId
            itemView.txv_movie_title.text = title
        }
    }

}
