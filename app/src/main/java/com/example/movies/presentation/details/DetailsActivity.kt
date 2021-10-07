package com.example.movies.presentation.details

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.movies.R

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

    private fun getDetails() {}
}