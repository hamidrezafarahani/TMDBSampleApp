package com.example.tmdbsampleapp.ui.movies

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.tmdbsampleapp.R
import com.example.tmdbsampleapp.databinding.FragmentMoviesBinding
import com.example.tmdbsampleapp.network.apierror.GlobalApiErrorHandler
import com.example.tmdbsampleapp.network.autoCleared
import com.example.tmdbsampleapp.network.dataBindings
import com.example.tmdbsampleapp.network.with
import com.example.tmdbsampleapp.utils.log
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
        binding.lifecycleOwner = viewLifecycleOwner
        adapter = MoviesAdapter {
            requireActivity() log it.title
        }

        viewModel.movies.with(viewLifecycleOwner).callbacks {
            onLoading {

            }
            onSuccess {
                adapter.submitList(it)
            }
            onFailed {
                apiErrorHandler.handleError(it)
            }
        }
    }
}