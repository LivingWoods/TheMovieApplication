package com.example.movieapplication.screens.searchmovie

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapplication.databinding.LayoutMovielistviewBinding
import com.example.movieapplication.domain.movie.DomainMovie

class SearchMovieAdapter(private val movieSet: LiveData<List<DomainMovie>>?) : RecyclerView.Adapter<SearchMovieAdapter.ViewHolder>() {
    lateinit var binding: LayoutMovielistviewBinding
    lateinit var context: Context

    inner class ViewHolder(val binding: LayoutMovielistviewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        binding = LayoutMovielistviewBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        context = viewGroup.context

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val posterUrl: String = movieSet?.value!![position].posterUrl

        binding.movieListViewTextViewMovieName.text = movieSet.value!![position].name
        binding.movieListViewReleaseDateTextView.text = movieSet.value!![position].releaseDate
        binding.movieListViewSynopsisTextView.text = movieSet.value!![position].synopsis
        Glide.with(context).load(posterUrl).into(binding.movieListViewImageView)
    }

    override fun getItemCount() = movieSet?.value?.size?:0
}