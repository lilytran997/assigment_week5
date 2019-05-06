package com.example.assigment_week5.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.assigment_week5.R
import com.example.assigment_week5.model.Movie
import kotlinx.android.synthetic.main.movie_item.view.*

class MovieAdapter(var items: ArrayList<Movie.Results>, private val context: Context?): RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    lateinit var mListener: ItemClickListenner
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {

        return MovieViewHolder(LayoutInflater.from(context).inflate(R.layout.movie_item,parent,false))
    }

    override fun getItemCount(): Int {
        return items.size

    }
    fun setListenner(listener: ItemClickListenner){
        this.mListener = listener
    }

    override fun onBindViewHolder(movieViewHolder: MovieViewHolder, position: Int) {

        movieViewHolder.name.text ="#$position ${items[position].title}"
        movieViewHolder.date.text = items[position].release_date
        movieViewHolder.description.text = items[position].overview
        context?.let {
            Glide.with(it)
                .load(	"https://image.tmdb.org/t/p/w500/"+ items[position].poster_path)
                .centerCrop()
                .into(movieViewHolder.movie)
        }
        movieViewHolder.itemView.setOnClickListener {
            mListener.onItemCLicked(position)
        }
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var movie = itemView.movie
        var name = itemView.name
        var date  = itemView.date
        var description = itemView.des
    }
}