package com.example.assigment_week5

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.example.assigment_week5.model.Movie
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {
    val TAG: String = DetailsActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.title = "Movie Details"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        getData()

    }

    private fun getData() {
        val data = intent.extras
        if (data != null) {
            val item: Item = data.getParcelable(CONSTANT_KEY) as Item
            val movie = data.getParcelable(MOVIE_KEY) as Movie.Results
            Log.e(TAG, item.toString())
            name_movie.text = movie.title
            date.text = movie.release_date
            overview.text = movie.overview
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500/${movie.backdrop_path}")
                .centerCrop()
                .into(movie_view)
            if(movie.video){
                video_play.visibility = View.VISIBLE
            }
            else{
                video_play.visibility = View.INVISIBLE
            }
            vote_rating.rating = (movie.vote_average/2).toFloat()
        }
    }
}
