package com.example.tmdbsampleapp.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdbsampleapp.data.remote.dto.Movie
import com.example.tmdbsampleapp.databinding.SearchItemBinding

class SearchMoviesAdapter(
    private val listener: (Movie) -> Unit
) : ListAdapter<Movie, SearchMoviesAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context)
        val binding = SearchItemBinding.inflate(layout, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
    }

    inner class ViewHolder(
        private val binding: SearchItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                val movie = getItem(absoluteAdapterPosition)
                listener(movie)
            }
        }

        fun bind(movie: Movie) = with(binding) {
            viewModel = ItemSearchMoviesViewModel(movie)
            executePendingBindings()
        }
    }

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
}