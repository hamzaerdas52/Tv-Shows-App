package com.hamzaerdas.tvshowsapp.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hamzaerdas.tvshowsapp.adapter.PopularTvShowListRecyclerAdapter
import com.hamzaerdas.tvshowsapp.databinding.ActivityTvShowsListBinding
import com.hamzaerdas.tvshowsapp.viewmodel.TvShowsListViewModel
import java.text.DecimalFormat

class TvShowsListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTvShowsListBinding
    private lateinit var viewModel: TvShowsListViewModel
    private val recyclerPopularTvShowAdapter = PopularTvShowListRecyclerAdapter(arrayListOf())
    var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTvShowsListBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)


        viewModel = ViewModelProviders.of(this@TvShowsListActivity)[TvShowsListViewModel::class.java]
        viewModel.refreshPopularData()

        binding.popularTvShowListRecyclerView.layoutManager = LinearLayoutManager(this@TvShowsListActivity)
        binding.popularTvShowListRecyclerView.adapter = recyclerPopularTvShowAdapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.tvShowProgressBar.visibility = View.VISIBLE
            binding.loadMessage.visibility = View.VISIBLE
            binding.showsErrorMessage.visibility = View.GONE
            binding.popularTvShowListRecyclerView.visibility = View.GONE
            viewModel.refreshPopularData()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        binding.popularTvShowListRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition = (binding.popularTvShowListRecyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                val totalItemCount = (binding.popularTvShowListRecyclerView.layoutManager as LinearLayoutManager).itemCount
                if(lastVisibleItemPosition == totalItemCount -1 ){
                    page++
                    viewModel.refreshOtherData(page)
                }
            }
        })

        observePopularLiveData()
    }

    private fun observePopularLiveData() {

        viewModel.popularTvShows.observe(this@TvShowsListActivity) {
            it?.let {
                binding.popularTvShowListRecyclerView.visibility = View.VISIBLE
                recyclerPopularTvShowAdapter.popularTvShowListUpdate(it.tvShows)
            }
        }

        viewModel.otherTvShows.observe(this@TvShowsListActivity){
            it?.let {
                binding.popularTvShowListRecyclerView.visibility = View.VISIBLE
                recyclerPopularTvShowAdapter.otherTvShowListUpdate(it.tvShows)
            }
        }

        viewModel.tvShowErrorMessage.observe(this@TvShowsListActivity) {
            it?.let {
                if (it) {
                    binding.showsErrorMessage.visibility = View.VISIBLE
                    binding.popularTvShowListRecyclerView.visibility = View.GONE
                } else {
                    binding.showsErrorMessage.visibility = View.GONE
                }
            }
        }

        viewModel.tvShowLoading.observe(this@TvShowsListActivity) {
            it?.let {
                if (it) {
                    binding.tvShowProgressBar.visibility = View.VISIBLE
                    binding.loadMessage.visibility = View.VISIBLE
                    binding.popularTvShowListRecyclerView.visibility = View.GONE
                    binding.showsErrorMessage.visibility = View.GONE
                } else {
                    binding.tvShowProgressBar.visibility = View.GONE
                    binding.loadMessage.visibility = View.GONE
                }
            }
        }
    }
}