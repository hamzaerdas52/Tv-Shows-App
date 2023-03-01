package com.hamzaerdas.tvshowsapp.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.hamzaerdas.tvshowsapp.databinding.ActivityTvShowsListBinding

class TvShowsListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTvShowsListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTvShowsListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

    }

}