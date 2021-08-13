package com.manuelsoft.supermovies.ui

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
import com.manuelsoft.supermovies.R
import com.manuelsoft.supermovies.business.GenreName
import com.manuelsoft.supermovies.data.MovieResult
import com.manuelsoft.supermovies.glide.GlideApp
import kotlinx.android.synthetic.main.card_view_inner_movie_list.view.*
import timber.log.Timber


class MovieListInnerAdapter(private val context: Context) : RecyclerView.Adapter<MovieListInnerAdapter.MyViewHolder>() {

    private var movieResultList: List<MovieResult>? = null
    private lateinit var requestListener :RequestListener<Drawable>

    fun setData(genreName: GenreName, movieResultList: List<MovieResult>?) {
        this.movieResultList = movieResultList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.card_view_inner_movie_list, parent, false)
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
        val url = "https://image.tmdb.org/t/p/w500" + movieResult?.poster_path

        val id = movieResult?.id
        val title = movieResult?.title
        Timber.i(" movie id = $id, title = $title")
        if (movieResult?.poster_path == null) {
            Timber.e("No image --> movie id = $id")
        }
        bindImage(movieResult, url, holder)

    }

    private fun bindImage(movieResult: MovieResult?, url: String?, holder: MyViewHolder) {
        if (movieResult != null) {
            setupGlideListener(holder.itemView.movie_progress_bar)
            holder.itemView.movie_progress_bar.visibility = View.VISIBLE
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
        private var progressBar: ProgressBar? = null
        fun setData(movieId : Long, title : String) {
            this.movieId = movieId
            this.progressBar = itemView.movie_progress_bar
            itemView.txv_movie_title.text = title
        }
    }

    private fun setupGlideListener(progressBar: ProgressBar) {
        requestListener = object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                progressBar.visibility = View.GONE
                Timber.i("Glide -> onLoadFailed()")
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
                Timber.i("Glide -> onResourceReady()")
                return false
            }

        }
    }

}
