package com.example.tmdbsampleapp.ui.movies

import android.os.Bundle
import android.view.View
import androidx.core.view.doOnNextLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdbsampleapp.R
import com.example.tmdbsampleapp.binding.dataBindings
import com.example.tmdbsampleapp.databinding.FragmentMoviesBinding
import com.example.tmdbsampleapp.extensions.autoCleared
import com.example.tmdbsampleapp.extensions.with
import com.example.tmdbsampleapp.network.apierror.GlobalApiErrorHandler
import com.example.tmdbsampleapp.util.setContentMaxWidth
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MoviesFragment : Fragment(R.layout.fragment_movies) {

    private val binding by dataBindings(FragmentMoviesBinding::bind)
    private val viewModel by viewModels<MoviesViewModel>()
    private val navController by lazy { findNavController() }
    private var adapter by autoCleared<MoviesAdapter>()


    @Inject
    lateinit var apiErrorHandler: GlobalApiErrorHandler

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        adapter = MoviesAdapter {
            Snackbar.make(view, it.title, Snackbar.LENGTH_LONG).show()
        }

        binding bindAdapter adapter

        binding.initObservers()
    }

    private fun initViews() = with(binding) {
        moviesViewModel = viewModel
        with(swipeRefreshLayout) {
            doOnNextLayout {
                setContentMaxWidth(it)
            }
            setOnRefreshListener {
                viewModel.loadFirstPage()
            }
        }
    }

    private fun FragmentMoviesBinding.initObservers() = with(viewModel) {
        movies.with(viewLifecycleOwner).callbacks {
            onLoading {
                loading = true
                error = false
            }
            onSuccess {
                loading = false
                error = false
                swipeRefreshLayout.isRefreshing = false
                cachedMovies.addAll(it)
                adapter.submitList(cachedMovies.toList())
            }
            onFailed {
                loading = false
                error = true
                apiErrorHandler.handleError(it) { _, message ->
                    Snackbar
                        .make(root, message, Snackbar.LENGTH_LONG)
                        .setAction("retry") {
                            handleInResponseError()
                        }
                        .show()
                }
            }
        }

        buttonRetry.setOnClickListener {
            handleInResponseError()
        }
    }

    private fun FragmentMoviesBinding.handleInResponseError() = with(viewModel) {
        if (cachedMovies.isEmpty()) {
            loadFirstPage()
        } else {
            loading = false
            error = false
            adapter.submitList(cachedMovies)
        }
    }

    private infix fun FragmentMoviesBinding.bindAdapter(adapter: MoviesAdapter) {
        rvListMovies.adapter = adapter
        rvListMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.loadNextPage()
                }
            }
        })
    }
}