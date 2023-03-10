package com.hamzaerdas.tvshowsapp.adapter

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import com.hamzaerdas.tvshowsapp.R
import com.hamzaerdas.tvshowsapp.databinding.TvShowRecyclerRowBinding
import com.hamzaerdas.tvshowsapp.model.Favorite
import com.hamzaerdas.tvshowsapp.model.TvShow
import com.hamzaerdas.tvshowsapp.service.TvShowDatabase
import com.hamzaerdas.tvshowsapp.view.TvShowDetailsActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class PopularTvShowListRecyclerAdapter(private val tvShowList: ArrayList<TvShow>):
    RecyclerView.Adapter<PopularTvShowListRecyclerAdapter.PopularTvShowViewHolder>(), CoroutineScope{

    var id: Int = 0

    class PopularTvShowViewHolder(val binding: TvShowRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularTvShowViewHolder {
        val binding = TvShowRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PopularTvShowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularTvShowViewHolder, position: Int) {

        val tvShow = tvShowList[holder.adapterPosition]

        holder.binding.tvshow = tvShow

        //getFavorite(holder.binding, tvShow)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, TvShowDetailsActivity::class.java)
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
        //tvShowList.clear()
        tvShowList.addAll(newTvShowList)
        notifyDataSetChanged()
    }

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    private fun getFavorite(binding: TvShowRecyclerRowBinding, tvShow: TvShow){
        launch {
            val favoriteList = TvShowDatabase(binding.root.context).getTvShowDao().getAllFavorite()
            favoriteList.forEach {
                if(tvShow.id == it.favoriteId){
                    println("${tvShow.name} ${tvShow.id} favorilerde ${it.favoriteId}")
                    binding.listFavoriteIcon.setImageResource(R.drawable.vote_star)
                } else {
                    binding.listFavoriteIcon.setImageResource(R.drawable.vote_star_false)
                }
            }
        }
    }
}