package com.example.tmdbsampleapp.ui.movies

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdbsampleapp.R
import com.example.tmdbsampleapp.databinding.FragmentMoviesBinding
import com.example.tmdbsampleapp.network.apierror.GlobalApiErrorHandler
import com.example.tmdbsampleapp.network.autoCleared
import com.example.tmdbsampleapp.network.dataBindings
import com.example.tmdbsampleapp.network.with
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MoviesFragment : Fragment(R.layout.fragment_movies) {

    @Inject
    lateinit var apiErrorHandler: GlobalApiErrorHandler

    private val navController: NavController by lazy { findNavController() }
    private val viewModel by viewModels<MoviesViewModel>()

    private val binding by dataBindings(FragmentMoviesBinding::bind)

    private var adapter by autoCleared<MoviesAdapter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        adapter = MoviesAdapter {
//            requireActivity() log it.title
        }
        binding bindAdapter adapter
        initObservers()
    }

    private fun initViews() = with(binding) {
        lifecycleOwner = viewLifecycleOwner
        vm = viewModel
    }

    private fun initObservers() = with(viewModel) {
        movies.with(viewLifecycleOwner).callbacks {
            onLoading {

            }
            onSuccess {
                cachedMovies.addAll(it)
                adapter.submitList(cachedMovies.toList())
            }
            onFailed {
                apiErrorHandler.handleError(it)
            }
        }
    }


    private infix fun FragmentMoviesBinding.bindAdapter(adapter: MoviesAdapter) {
        rvListMovies.adapter = adapter
        rvListMovies.layoutManager = LinearLayoutManager(rvListMovies.context)
        rvListMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.loadNextPage()
                }
            }

            // OR using this callback
            /**
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val lm = recyclerView.layoutManager as LinearLayoutManager
            val lastVisiblePosition = lm.findLastVisibleItemPosition()
            if (lastVisiblePosition == lm.itemCount - 3) {
            viewModel.loadNextPage()
            }

            }*/
        })
    }
}


