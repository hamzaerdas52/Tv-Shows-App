package com.hamzaerdas.tvshowsapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hamzaerdas.tvshowsapp.R
import com.hamzaerdas.tvshowsapp.databinding.TvShowRecyclerRowBinding
import com.hamzaerdas.tvshowsapp.model.TvShow
import com.hamzaerdas.tvshowsapp.service.FavoriteDao
import com.hamzaerdas.tvshowsapp.service.TvShowDatabase
import com.hamzaerdas.tvshowsapp.view.DetailsActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class PopularTvShowListRecyclerAdapter @Inject constructor(
    private val favoriteDao: FavoriteDao,
) :
    RecyclerView.Adapter<PopularTvShowListRecyclerAdapter.PopularTvShowViewHolder>(),
    CoroutineScope {

    private val tvShowList = ArrayList<TvShow>()

    class PopularTvShowViewHolder(val binding: TvShowRecyclerRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularTvShowViewHolder {
        val binding =
            TvShowRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PopularTvShowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularTvShowViewHolder, position: Int) {

        val tvShow = tvShowList[holder.adapterPosition]

        holder.binding.tvshow = tvShow

        getFavorite(holder.binding, tvShow)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailsActivity::class.java)
            intent.putExtra("id", tvShowList[position].id)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return tvShowList.size
    }

    fun popularTvShowListUpdate(newTvShowList: List<TvShow>) {
        tvShowList.clear()
        tvShowList.addAll(newTvShowList)
        notifyDataSetChanged()
    }

    fun otherTvShowListUpdate(newTvShowList: List<TvShow>) {
        tvShowList.addAll(newTvShowList)
        notifyDataSetChanged()
    }

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    private fun getFavorite(binding: TvShowRecyclerRowBinding, tvShow: TvShow) {
        launch {
            val isFavorite =
                favoriteDao.hasBeenAdded(tvShow.id!!)
            if (isFavorite == 1) {
                binding.listFavoriteIcon.setImageResource(R.drawable.vote_star)
            } else {
                binding.listFavoriteIcon.setImageResource(R.drawable.vote_star_false)
            }
        }
    }
}