package com.example.tmdbsampleapp.ui.search

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdbsampleapp.R
import com.example.tmdbsampleapp.binding.dataBindings
import com.example.tmdbsampleapp.databinding.FragmentSearchBinding
import com.example.tmdbsampleapp.extensions.autoCleared
import com.example.tmdbsampleapp.extensions.with
import com.example.tmdbsampleapp.network.apierror.GlobalApiErrorHandler
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val binding by dataBindings(FragmentSearchBinding::bind)
    private val viewModel by viewModels<SearchViewModel>()
    private var adapter by autoCleared<SearchMoviesAdapter>()

    @Inject
    lateinit var apiErrorHandler: GlobalApiErrorHandler

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SearchMoviesAdapter {
            Snackbar.make(view, it.title, Snackbar.LENGTH_LONG).show()
        }
        binding.rvListMovies.adapter = adapter
        binding.rvListMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.loadNextPage()
                }
            }
        })

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.search_menu, menu)
                val searchViewItem = menu.findItem(R.id.search_view)
                val searchView = searchViewItem.actionView as SearchView
                searchView.apply {

                    var queryText: String? = null
                    setOnSearchClickListener {
                        setQuery(queryText, false)
                    }

                    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            queryText = query
                            query?.let { binding.search(it) }
                            return true
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            return false
                        }
                    })
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.search_view -> true
                    R.id.clear_search_query -> with(viewModel) {
                        cachedMovies.clear()
                        adapter.submitList(cachedMovies.toList())
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun FragmentSearchBinding.search(query: String) = with(viewModel) {
        search(query).with(viewLifecycleOwner).callbacks {
            onLoading {
                loading = true
                error = false
            }
            onSuccess {
                loading = false
                error = false
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
                            if (cachedMovies.isEmpty()) {
                                loadFirstPage()
                            } else {
                                loading = false
                                error = false
                                adapter.submitList(cachedMovies.toList())
                            }
                        }
                        .show()
                }
            }
        }
    }
}