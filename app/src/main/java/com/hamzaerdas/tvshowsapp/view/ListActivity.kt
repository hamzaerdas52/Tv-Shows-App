package com.hamzaerdas.tvshowsapp.view

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hamzaerdas.tvshowsapp.adapter.PopularTvShowListRecyclerAdapter
import com.hamzaerdas.tvshowsapp.databinding.ActivityTvShowsListBinding
import com.hamzaerdas.tvshowsapp.viewmodel.ListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTvShowsListBinding
    private val viewModel: ListViewModel by viewModels()
    @Inject lateinit var recyclerPopularTvShowAdapter: PopularTvShowListRecyclerAdapter
    var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTvShowsListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        viewModelInitialize()
        layoutManagerInitialize()
        recyclerViewInitialize()
        swipeRefresh()
        infiniteScroll()
        observePopularLiveData()
        tvShowUpdated()

    }

    private fun viewModelInitialize() {
        viewModel.refreshPopularData()
    }

    private fun recyclerViewInitialize() {
        binding.popularTvShowListRecyclerView.adapter = recyclerPopularTvShowAdapter
    }

    private fun layoutManagerInitialize() {
        binding.popularTvShowListRecyclerView.layoutManager =
            LinearLayoutManager(this@ListActivity)
    }

    private fun observePopularLiveData() {

        viewModel.popularTvShows.observe(this@ListActivity) {
            it?.let {
                binding.popularTvShowListRecyclerView.visibility = View.VISIBLE
                recyclerPopularTvShowAdapter.popularTvShowListUpdate(it.tvShows)
            }
        }

        viewModel.otherTvShows.observe(this@ListActivity) {
            it?.let {
                binding.popularTvShowListRecyclerView.visibility = View.VISIBLE
                recyclerPopularTvShowAdapter.otherTvShowListUpdate(it.tvShows)
            }
        }

        viewModel.tvShowErrorMessage.observe(this@ListActivity) {
            it?.let {
                if (it) {
                    binding.showsErrorMessage.visibility = View.VISIBLE
                    binding.popularTvShowListRecyclerView.visibility = View.GONE
                } else {
                    binding.showsErrorMessage.visibility = View.GONE
                }
            }
        }

        viewModel.tvShowLoading.observe(this@ListActivity) {
            it?.let {
                if (it) {
                    binding.tvShowProgressBar.visibility = View.VISIBLE
                    binding.loadMessage.visibility = View.VISIBLE
                    binding.tvShowInfiniteProgressBar.visibility = View.GONE
                    binding.popularTvShowListRecyclerView.visibility = View.GONE
                    binding.showsErrorMessage.visibility = View.GONE
                } else {
                    binding.tvShowProgressBar.visibility = View.GONE
                    binding.loadMessage.visibility = View.GONE
                }
            }
        }

        viewModel.tvShowInfiniteLoading.observe(this@ListActivity) {
            it?.let {
                if (it) {
                    binding.tvShowInfiniteProgressBar.visibility = View.VISIBLE
                    binding.showsErrorMessage.visibility = View.GONE
                } else {
                    binding.tvShowInfiniteProgressBar.visibility = View.GONE
                }
            }
        }

        viewModel.popularTvShowUpdated.observe(this@ListActivity) {
            it?.let {
                if (it) {
                    binding.updateListButton.visibility = View.VISIBLE
                } else {
                    binding.updateListButton.visibility = View.GONE
                }
            }
        }
    }

    private fun infiniteScroll() {
        binding.popularTvShowListRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastVisibleItemPosition =
                    (binding.popularTvShowListRecyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                val totalItemCount =
                    (binding.popularTvShowListRecyclerView.layoutManager as LinearLayoutManager).itemCount
                if (lastVisibleItemPosition == totalItemCount - 1) {
                    page++
                    viewModel.refreshOtherData(page)
                }
            }
        })
    }

    private fun swipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.tvShowProgressBar.visibility = View.VISIBLE
            binding.loadMessage.visibility = View.VISIBLE
            binding.showsErrorMessage.visibility = View.GONE
            binding.popularTvShowListRecyclerView.visibility = View.GONE
            viewModel.refreshFromAPI()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun tvShowUpdated() {
        binding.updateListButton.setOnClickListener {
            binding.popularTvShowListRecyclerView.scrollToPosition(0)
            viewModel.refreshPopularData()
        }
    }

    override fun onResume() {
        super.onResume()
        recyclerPopularTvShowAdapter.notifyDataSetChanged()
    }

}