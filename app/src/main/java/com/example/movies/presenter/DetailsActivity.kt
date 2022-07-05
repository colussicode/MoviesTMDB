package com.example.movies.presenter

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.movies.BASE_URL
import com.example.movies.MovieDetails
import com.example.movies.MoviesData
import com.example.movies.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailsActivity : AppCompatActivity() {
    lateinit var movieImageView: ImageView
    lateinit var movieTitle: TextView
    lateinit var tagLine: TextView
    lateinit var rlsDate: TextView
    lateinit var status: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        getDetails()
    }

    private fun getDetails() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(MoviesData::class.java)

        val movieId = intent.extras?.getInt("id")

        movieId?.let { retrofitBuilder.getMovieDetails(movieId, "39fd0a08c0cc7fd3041fc14605c22358") }
            ?.enqueue(object : Callback<MovieDetails?> {
                override fun onResponse(
                    call: Call<MovieDetails?>,
                    response: Response<MovieDetails?>
                ) {
                    response.body()?.let {
                        movieImageView = findViewById(R.id.movie_img_details)
                        movieTitle = findViewById(R.id.movie_title_details)
                        tagLine = findViewById(R.id.tag_line_details)
                        rlsDate = findViewById(R.id.release_date_details)
                        status = findViewById(R.id.status_details)

                        val IMG_BASE = "https://image.tmdb.org/t/p/w500/"

                        Glide.with(baseContext)
                            .load(IMG_BASE + it.poster_path)
                            .into(movieImageView)

                        movieTitle.text = it.title
                        tagLine.text = it.tagline
                        rlsDate.text = it.release_date
                        status.text = it.status
                    }
                }

                override fun onFailure(call: Call<MovieDetails?>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }
}