package com.hamzaerdas.tvshowsapp.view


import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.hamzaerdas.tvshowsapp.adapter.detailactivity.TvShowDetailGenresAdapter
import com.hamzaerdas.tvshowsapp.databinding.ActivityTvShowDetailsBinding
import com.hamzaerdas.tvshowsapp.util.dowloadImage
import com.hamzaerdas.tvshowsapp.util.makePlaceHolder
import com.hamzaerdas.tvshowsapp.viewmodel.TvShowDetailsViewModel


class TvShowDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTvShowDetailsBinding
    private lateinit var viewModel: TvShowDetailsViewModel
    private lateinit var genresAdapter: TvShowDetailGenresAdapter
    private var list = ArrayList<Any>()
    var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTvShowDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        genresAdapter = TvShowDetailGenresAdapter(arrayListOf())

        id = intent.getIntExtra("id", 0)
        println(id)

        viewModel = ViewModelProviders.of(this@TvShowDetailsActivity)[TvShowDetailsViewModel::class.java]
        viewModel.getDataDetails(id)

        binding.genresRecyclerView.layoutManager = LinearLayoutManager(this@TvShowDetailsActivity, LinearLayoutManager.HORIZONTAL, false)
        binding.genresRecyclerView.adapter = genresAdapter

        binding.include.goBackIcon.setOnClickListener {
            finish()
        }

        observeLiveData()
    }

    fun observeLiveData() {

        viewModel.tvShowsDetail.observe(this@TvShowDetailsActivity) {
            it?.let { it ->
                binding.tvShowImage.dowloadImage(it.poster, makePlaceHolder(this@TvShowDetailsActivity))
                binding.include.topBarTextView.text = "${it.name}"
                binding.tvShowName.text = it.name
                binding.tvShowSeasonCount.text = it.seasonsCount.toString()
                binding.tvShowEpisodeCount.text = it.episodesCount.toString()
                //String sayi = String.valueOf(String.format("%10.2f", sayi1f)) + "√3";
                binding.vote.text = it.vote.toString()
                genresAdapter.genresUpdate(it.genres)
                binding.tvShowDetail.text = it.detail
            }
        }

        viewModel.tvShowDetailLoading.observe(this@TvShowDetailsActivity){
            it?.let {
                if(it){
                    binding.detailProgressBar.visibility = View.VISIBLE
                    binding.linearLayout.visibility = View.GONE
                    binding.include.root.visibility = View.GONE
                } else{
                    binding.detailProgressBar.visibility = View.GONE
                    binding.linearLayout.visibility = View.VISIBLE
                    binding.include.root.visibility = View.VISIBLE
                }
            }
        }
    }
}

