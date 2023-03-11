package com.hamzaerdas.tvshowsapp.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.hamzaerdas.tvshowsapp.R
import com.hamzaerdas.tvshowsapp.adapter.detailactivity.TvShowDetailGenresAdapter
import com.hamzaerdas.tvshowsapp.databinding.ActivityTvShowDetailsBinding
import com.hamzaerdas.tvshowsapp.model.Favorite
import com.hamzaerdas.tvshowsapp.service.TvShowDatabase
import com.hamzaerdas.tvshowsapp.util.dowloadImage
import com.hamzaerdas.tvshowsapp.util.makePlaceHolder
import com.hamzaerdas.tvshowsapp.viewmodel.BaseViewModel
import com.hamzaerdas.tvshowsapp.viewmodel.TvShowDetailsViewModel
import kotlinx.coroutines.launch


class TvShowDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTvShowDetailsBinding
    private lateinit var viewModel: TvShowDetailsViewModel
    private lateinit var baseViewModel: BaseViewModel
    private lateinit var genresAdapter: TvShowDetailGenresAdapter
    private lateinit var favorite: Favorite
    var isFavorite: Boolean = false
    var id = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTvShowDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        getId()
        viewModelInitialize()
        baseViewModelInitialize()
        recyclerViewInitialize()
        goBack()
        observeLiveData()

        getFavorite()

        binding.include.detailFavoriteIcon.setOnClickListener {
            if(isFavorite){
                binding.include.detailFavoriteIcon.setImageResource(R.drawable.vote_star_false)
                baseViewModel.deleteFavorite(favorite)
                isFavorite = false
            } else {
                binding.include.detailFavoriteIcon.setImageResource(R.drawable.vote_star)
                baseViewModel.addFavorite(favorite)
                isFavorite = true
            }
        }

    }

    private fun getId() {
        id = intent.getIntExtra("id", 0)
        favorite = Favorite(id)
    }

    private fun observeLiveData() {

        viewModel.tvShowsDetail.observe(this@TvShowDetailsActivity) {
            it?.let { it ->

                binding.include.showName = it

                binding.tvDetail = it

                genresAdapter.genresUpdate(it.genres)

                if (it.detail == "") {
                    binding.tvShowDetailText.visibility = View.GONE
                    binding.tvShowDetail.visibility = View.GONE
                } else {
                    binding.tvShowDetail.text = it.detail
                }
            }
        }

        viewModel.tvShowDetailLoading.observe(this@TvShowDetailsActivity) {
            it?.let {
                if (it) {
                    binding.detailProgressBar.visibility = View.VISIBLE
                    binding.linearLayout.visibility = View.GONE
                    binding.include.root.visibility = View.GONE
                } else {
                    binding.detailProgressBar.visibility = View.GONE
                    binding.linearLayout.visibility = View.VISIBLE
                    binding.include.root.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun viewModelInitialize() {
        viewModel = ViewModelProviders.of(this@TvShowDetailsActivity)[TvShowDetailsViewModel::class.java]
        viewModel.getDataDetails(id)
    }

    private fun baseViewModelInitialize(){
        baseViewModel = ViewModelProviders.of(this@TvShowDetailsActivity)[BaseViewModel::class.java]
    }

    private fun recyclerViewInitialize() {
        genresAdapter = TvShowDetailGenresAdapter(arrayListOf())
        binding.genresRecyclerView.layoutManager =
            LinearLayoutManager(this@TvShowDetailsActivity, LinearLayoutManager.HORIZONTAL, false)
        binding.genresRecyclerView.adapter = genresAdapter
    }

    private fun goBack() {
        binding.include.goBackIcon.setOnClickListener {
            finish()
        }
    }

    private fun getFavorite(){
        lifecycleScope.launch{
            val favorite = TvShowDatabase(this@TvShowDetailsActivity).getTvShowDao().hasBeenAdded(id)
            if(favorite == 1){
                binding.include.detailFavoriteIcon.setImageResource(R.drawable.vote_star)
                isFavorite = true
            }
        }
    }

}

