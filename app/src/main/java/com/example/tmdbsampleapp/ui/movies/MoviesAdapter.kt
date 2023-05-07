package com.example.tmdbsampleapp.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdbsampleapp.R
import com.example.tmdbsampleapp.data.remote.dto.Movie
import com.example.tmdbsampleapp.databinding.MovieItemBinding

class MoviesAdapter(private val listener: (Movie) -> Unit) :
    ListAdapter<Movie, MoviesAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    inner class ViewHolder(
        private val binding: MovieItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            // define this logic in fragment
            itemView.setOnClickListener {
                val movie = getItem(absoluteAdapterPosition)
                listener(movie)
            }

            // define this logic in item view model
            itemView.setOnClickListener {
                binding.viewModel?.onClick(it)
            }

//            itemView.setOnClickListener(binding.viewModel!!::onClick)
        }

        fun bind(movie: Movie) {
            with(binding) {
                viewModel = ItemMovieViewModel(movie)
                executePendingBindings()
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.movie_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}