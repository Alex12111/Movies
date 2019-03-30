package com.example.aleks.movies.presentation.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.aleks.movies.R
import com.example.aleks.movies.data.models.content.MoviesValue
import com.squareup.picasso.Picasso


class MoviesAdapter(internal var mMoviesList: MutableList<MoviesValue>?) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var i = ""
        mMoviesList = ArrayList(LinkedHashSet<MoviesValue>(mMoviesList))
        val movie = mMoviesList!![position]

        Picasso.get()
                .load(movie.image)
                .error(android.R.drawable.stat_notify_error)
                .fit()
                .into(holder.mImage)

        holder.mTitle.text = movie.title
        holder.mYear.text = movie.year.toString()
        holder.mDirector.text = movie.director
        holder.mDescription.text = movie.desription

        val iter = movie.genre!!.iterator()
        while (iter.hasNext()) {
            i += iter.next() + ","
        }
        i = i.substring(0, i.length - 1)
        holder.mGenre.text = i
    }

    override fun getItemCount(): Int {
        return mMoviesList?.size ?: 0
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mImage: ImageView
        var mTitle: TextView
        var mYear: TextView
        var mDirector: TextView
        var mGenre: TextView
        var mDescription: TextView

        init {
            mTitle = itemView.findViewById(R.id.tv_title)
            mImage = itemView.findViewById(R.id.iv_image)
            mYear = itemView.findViewById(R.id.tv_year)
            mDirector = itemView.findViewById(R.id.tv_director)
            mGenre = itemView.findViewById(R.id.tv_genre)
            mDescription = itemView.findViewById(R.id.tv_desctiption)
        }
    }
}