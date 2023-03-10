package com.hamzaerdas.tvshowsapp.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hamzaerdas.tvshowsapp.R

private val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500/"

fun ImageView.dowloadImage(url: String?, placeholder: CircularProgressDrawable) {

    val options = RequestOptions().placeholder(placeholder).error(R.mipmap.ic_launcher_round)
    Glide.with(context).setDefaultRequestOptions(options).load("$IMAGE_BASE_URL$url").into(this)
}

fun makePlaceHolder(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 35f
        start()
    }
}

@BindingAdapter("android:dowloadImage")
fun dowloadImage(view: ImageView, url: String?){
    view.dowloadImage(url, makePlaceHolder(view.context))
}