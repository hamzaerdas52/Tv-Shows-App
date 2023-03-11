package com.hamzaerdas.tvshowsapp.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import com.hamzaerdas.tvshowsapp.R
import com.hamzaerdas.tvshowsapp.databinding.ActivitySplashScreenBinding

@Suppress("DEPRECATION")
@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private val splashDuration = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieAnimationBefore = AnimationUtils.loadAnimation(this, R.anim.animation_movie_before)
        val movieAnimationAfter = AnimationUtils.loadAnimation(this, R.anim.animation_movie_after)
        val textAnimation = AnimationUtils.loadAnimation(this, R.anim.animation_app_text)

        val movieBeforeImage = binding.movieBefore
        val movieAfterImage = binding.movieAfter
        val appText = binding.appText

        movieBeforeImage.animation = movieAnimationBefore
        movieAfterImage.animation = movieAnimationAfter
        appText.animation = textAnimation

        Handler().postDelayed({
            val intent = Intent(this@SplashScreenActivity, TvShowsListActivity::class.java)
            startActivity(intent)
            finish()
        }, splashDuration.toLong())
    }
}