package com.example.tmdbsampleapp.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import com.example.tmdbsampleapp.R
import com.example.tmdbsampleapp.binding.dataBindings
import com.example.tmdbsampleapp.databinding.FragmentSearchBinding
import com.example.tmdbsampleapp.extensions.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val binding by dataBindings(FragmentSearchBinding::bind)
    private val viewModel by viewModels<SearchViewModel>()
    private val adapter by autoCleared<SearchMoviesAdapter>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}