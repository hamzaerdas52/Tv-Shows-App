package com.hamzaerdas.tvshowsapp.view

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.hamzaerdas.tvshowsapp.R
import com.hamzaerdas.tvshowsapp.adapter.detailactivity.TvShowDetailGenresAdapter
import com.hamzaerdas.tvshowsapp.databinding.ActivityTvShowDetailsBinding
import com.hamzaerdas.tvshowsapp.model.Favorite
import com.hamzaerdas.tvshowsapp.viewmodel.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTvShowDetailsBinding
    private val viewModel: DetailsViewModel by viewModels()
    private lateinit var genresAdapter: TvShowDetailGenresAdapter
    private lateinit var favorite: Favorite
    private var isFavorite: Boolean = false
    var id = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTvShowDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        getId()
        viewModelInitialize()
        recyclerViewInitialize()
        goBack()
        observeLiveData()
        getFavorite()

    }

    private fun getId() {
        id = intent.getIntExtra("id", 0)
        favorite = Favorite(id)
    }

    private fun viewModelInitialize() {
        viewModel.getDataDetails(id)
        viewModel.isFavorite(id)
    }

    private fun recyclerViewInitialize() {
        genresAdapter = TvShowDetailGenresAdapter(arrayListOf())
        binding.genresRecyclerView.layoutManager =
            LinearLayoutManager(this@DetailsActivity, LinearLayoutManager.HORIZONTAL, false)
        binding.genresRecyclerView.adapter = genresAdapter
    }

    private fun observeLiveData() {

        viewModel.tvShowsDetail.observe(this@DetailsActivity) {
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

        viewModel.tvShowDetailLoading.observe(this@DetailsActivity) {
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

        viewModel.isFavorite.observe(this@DetailsActivity) {
            it?.let {
                if (it) {
                    binding.include.detailFavoriteIcon.setImageResource(R.drawable.vote_star)
                    isFavorite = true
                }
            }
        }
    }

    private fun getFavorite() {
        binding.include.detailFavoriteIcon.setOnClickListener {
            if (isFavorite) {
                binding.include.detailFavoriteIcon.setImageResource(R.drawable.vote_star_false)
                viewModel.deleteFavorite(favorite)
                isFavorite = false
            } else {
                binding.include.detailFavoriteIcon.setImageResource(R.drawable.vote_star)
                viewModel.addFavorite(favorite)
                isFavorite = true
            }
        }
    }

    private fun goBack() {
        binding.include.goBackIcon.setOnClickListener {
            finish()
        }
    }

}

