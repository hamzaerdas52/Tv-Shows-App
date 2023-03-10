package com.hamzaerdas.tvshowsapp.adapter.detailactivity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hamzaerdas.tvshowsapp.databinding.DetailGenresRecyclerRowBinding
import com.hamzaerdas.tvshowsapp.model.TvShowGenres

class TvShowDetailGenresAdapter (private val genresList: ArrayList<TvShowGenres>) : RecyclerView.Adapter<TvShowDetailGenresAdapter.GenresViewHolder>() {
    class GenresViewHolder (val binding: DetailGenresRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresViewHolder {
        val binding = DetailGenresRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenresViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenresViewHolder, position: Int) {
        val genres = genresList[holder.adapterPosition]
        holder.binding.genres = genres
    }

    override fun getItemCount(): Int {
        return genresList.size
    }

    fun genresUpdate(newGenresList: List<TvShowGenres>) {
        genresList.clear()
        genresList.addAll(newGenresList)
        notifyDataSetChanged()
    }
}