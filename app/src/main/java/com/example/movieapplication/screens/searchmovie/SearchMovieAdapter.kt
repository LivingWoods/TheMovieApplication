package com.example.movieapplication.screens.searchmovie

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapplication.R
import com.example.movieapplication.databinding.LayoutMovielistviewBinding
import com.example.movieapplication.domain.movies.DomainMovie
import com.example.movieapplication.utils.setMovieName
import com.example.movieapplication.utils.setMoviePoster
import com.example.movieapplication.utils.setMovieReleaseDate
import com.example.movieapplication.utils.setSynopsis

class SearchMovieAdapter(private val movieSet: LiveData<List<DomainMovie>?>?) : RecyclerView.Adapter<SearchMovieAdapter.ViewHolder>() {
    lateinit var binding: LayoutMovielistviewBinding
    lateinit var context: Context

    inner class ViewHolder(val binding: LayoutMovielistviewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        binding = LayoutMovielistviewBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        context = viewGroup.context

        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (movieSet?.value != null) {
            binding.movieListViewTextViewMovieName.setMovieName(movieSet.value!![position].name)
            binding.movieListViewReleaseDateTextView.setMovieReleaseDate(movieSet.value!![position].releaseDate)
            binding.movieListViewSynopsisTextView.setSynopsis(movieSet.value!![position].synopsis)
            binding.movieListViewImageView.setMoviePoster(movieSet.value!![position].posterUrl)
        } else
        {
            // TODO: handle no results
        }
    }

    override fun getItemCount() = movieSet?.value?.size?:0
}