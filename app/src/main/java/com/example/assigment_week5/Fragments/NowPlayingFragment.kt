package com.example.assigment_week5.Fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.assigment_week5.*
import com.example.assigment_week5.Adapter.ItemClickListenner
import com.example.assigment_week5.Adapter.MovieAdapter
import com.example.assigment_week5.model.*

import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_now_playing.*
import okhttp3.*
import java.io.IOException
@SuppressLint("SetTextI18n")

class NowPlayingFragment : Fragment() {



    var movies: ArrayList<Movie.Results> = ArrayList()
    lateinit var movieAdapter: MovieAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addMovie()
        movieAdapter = MovieAdapter(movies,activity)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_now_playing, container, false)
        //val listMovie = view.findViewById(R.id.list_movie) as RecyclerView
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list_movie.layoutManager = LinearLayoutManager(context)
        list_movie.adapter = movieAdapter
          //set onclick item movie
        movieAdapter.setListenner(movieItemCLickListener)
        swipeRefresh.setOnRefreshListener{
            loadItems()
        }
        swipeRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
            android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light)

    }
    var movieFresh:ArrayList<Movie.Results> = ArrayList()
    private fun loadItems() {
        movieAdapter.clear()
        addFresh()
        onItemsLoadComplete()
    }

    private fun addFresh() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://api.themoviedb.org/3/movie/now_playing?api_key=7519cb3f829ecd53bd9b7007076dbe23")
            .build()
        client.newCall(request)
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    activity!!.runOnUiThread {
                        call.cancel()
                    }
                }
                override fun onResponse(call: Call, response: Response) {
                    val json = response.body()!!.string()
                    val datagson = Gson().fromJson(json, Movie.ResultArray::class.java)
                    activity!!.runOnUiThread {
                        for (i in datagson.results) {
                            movieFresh.add(i)
                        }
                        movieAdapter.addAll(movieFresh)

                    }
                }
            })
    }

    private fun onItemsLoadComplete() {
        swipeRefresh.isRefreshing = false
    }

    private fun addMovie() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://api.themoviedb.org/3/movie/now_playing?api_key=7519cb3f829ecd53bd9b7007076dbe23")
            .build()
        client.newCall(request)
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    activity!!.runOnUiThread {
                        call.cancel()
                    }
                }
                override fun onResponse(call: Call, response: Response) {
                    val json = response.body()!!.string()
                    val datagson = Gson().fromJson(json, Movie.ResultArray::class.java)
                    activity!!.runOnUiThread {
                        movies.clear()
                        for (i in datagson.results) {
                            movies.add(i)
                            movieAdapter.notifyDataSetChanged()
                        }
                    }
                }
            })


    }
    private val movieItemCLickListener = object : ItemClickListenner {

        override fun onItemCLicked(position: Int) {
            var category = Category(categoryId = 1, categoryName = "MacBook")
            var item = Item(
                imageId = 2,
                price = 30.0,
                title = "MacBook Pro",
                category = category
            )
            val intent = Intent(activity, DetailsActivity::class.java)
            intent.putExtra(MOVIE_KEY, movies[position])
            intent.putExtra(CONSTANT_KEY, item)
            startActivity(intent)
        }
    }
}

