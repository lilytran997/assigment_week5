package com.example.assigment_week5.Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.assigment_week5.Adapter.ItemClickListenner
import com.example.assigment_week5.Adapter.MovieAdapter

import com.example.assigment_week5.R
import com.example.assigment_week5.model.Movie
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_top_rate.*
import okhttp3.*
import java.io.IOException

class TopRateFragment : Fragment() {

    var movie_rate: ArrayList<Movie.Results> = ArrayList()
    lateinit var toprateAdapter: MovieAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addMovie()
        toprateAdapter = MovieAdapter(movie_rate,activity)
    }

    private fun addMovie() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://api.themoviedb.org/3/movie/top_rated?api_key=7519cb3f829ecd53bd9b7007076dbe23")
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
                        movie_rate.clear()
                        for (i in datagson.results) {
                            movie_rate.add(i)
                            toprateAdapter.notifyDataSetChanged()
                        }
                    }
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top_rate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        top_rate.layoutManager = LinearLayoutManager(context)
        top_rate.adapter = toprateAdapter
        toprateAdapter.setListenner(movieItemCLickListener)
    }
    private val movieItemCLickListener = object : ItemClickListenner {

        override fun onItemCLicked(position: Int) {

        }
    }
}
