package com.hamzaerdas.tvshowsapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.hamzaerdas.tvshowsapp.databinding.TvShowRecyclerRowBinding
import com.hamzaerdas.tvshowsapp.model.TvShow
import com.hamzaerdas.tvshowsapp.util.dowloadImage
import com.hamzaerdas.tvshowsapp.util.makePlaceHolder
import com.hamzaerdas.tvshowsapp.view.TvShowDetailsActivity

class PopularTvShowListRecyclerAdapter(private val tvShowList: ArrayList<TvShow>) :
    RecyclerView.Adapter<PopularTvShowListRecyclerAdapter.PopularTvShowViewHolder>() {

    class PopularTvShowViewHolder(val binding: TvShowRecyclerRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularTvShowViewHolder {
        val binding =
            TvShowRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PopularTvShowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularTvShowViewHolder, position: Int) {
        if (tvShowList[position].name == "") {
            holder.binding.recyclerTvShowName.text = "Değer Yok!"
        } else {
            holder.binding.recyclerTvShowName.text = tvShowList[position].name
        }

        if (tvShowList[position].vote.toString() == "") {
            holder.binding.recyclerTvShowVote.text = "Değer Yok!"
        } else {
            holder.binding.recyclerTvShowVote.text = tvShowList[position].vote.toString()
        }

        holder.binding.recyclerImageView.dowloadImage(
            tvShowList[position].poster,
            makePlaceHolder(holder.itemView.context)
        )

        holder.binding.listFavoriteIcon.setOnClickListener {
            Toast.makeText(it.context, "${tvShowList[position].name} Favorilere eklendi", Toast.LENGTH_SHORT).show()
        }

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
}