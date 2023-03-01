package com.hamzaerdas.tvshowsapp.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hamzaerdas.tvshowsapp.databinding.ActivityTvShowDetailsBinding

class TvShowDetailsActivity : AppCompatActivity(){
    private lateinit var binding: ActivityTvShowDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTvShowDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val name = intent.getStringExtra("name")
        println(name)

    }
}