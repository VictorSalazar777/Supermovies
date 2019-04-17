package com.manuelsoft.movies2.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.manuelsoft.movies2.MovieInfo
import com.manuelsoft.movies2.R
import com.manuelsoft.movies2.Utils.Companion.convertDpToPixel
import com.manuelsoft.movies2.glide.GlideApp
import kotlinx.android.synthetic.main.card_view_inner_movie_list.view.*


class MovieListInnerAdapter(private val context: Context) : RecyclerView.Adapter<MovieListInnerAdapter.MyViewHolder>() {

    private var movieCategoriesList: List<MovieInfo>? = null
    private val circularProgressDrawable = CircularProgressDrawable(context)
    private var requestListener :RequestListener<Drawable>

    init {
        circularProgressDrawable.apply {
            centerRadius = convertDpToPixel(50f, context)
            strokeWidth = convertDpToPixel(10f, context)
        }

        requestListener = object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                circularProgressDrawable.stop()
                return true
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                circularProgressDrawable.stop()
                return true
            }

        }
    }

    fun setData(movieCategoriesList: List<MovieInfo>) {
        this.movieCategoriesList = movieCategoriesList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.card_view_inner_movie_list, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        if (movieCategoriesList == null) {
            return 0
        }
        return movieCategoriesList!!.size
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movieInfo = movieCategoriesList?.get(position)
        val url = movieInfo?.imageUrl
        bindImage(movieInfo, url, holder)

    }

    private fun bindImage(movieInfo: MovieInfo?, url: String?, holder: MyViewHolder) {
    //    holder.itemView.iv_movie_image.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.placeholder))
        if (movieInfo != null) {
            circularProgressDrawable.start()
            GlideApp.with(context)
                .load(url)
                .placeholder(circularProgressDrawable)
                //.listener(requestListener)
                .error(R.drawable.placeholder)
                .priority(Priority.HIGH)
                .into(holder.itemView.iv_movie_image)
            holder.setData(movieInfo.id, movieInfo.title)
        } else {
            GlideApp.with(context).clear(holder.itemView.iv_movie_image)
            holder.itemView.iv_movie_image.setImageDrawable(null)
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var movieId : Int? = null
        fun setData(movieId : Int, title : String) {
            this.movieId = movieId
            itemView.txv_movie_title.text = title
        }
    }

}
