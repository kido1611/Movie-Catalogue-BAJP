package com.kido1611.dicoding.moviecatalogue.fragment.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.kido1611.dicoding.moviecatalogue.R
import com.kido1611.dicoding.moviecatalogue.adapter.LoadStateAdapter
import com.kido1611.dicoding.moviecatalogue.databinding.MoviesFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private val viewModel: MoviesViewModel by viewModels()
    private var binding: MoviesFragmentBinding? = null
    private lateinit var movieAdapter: MoviePagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = MoviesFragmentBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    @ExperimentalPagingApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieAdapter = MoviePagingAdapter()
        movieAdapter.addLoadStateListener {
            val isRefresh =
                it.refresh is LoadState.Loading || it.mediator?.refresh is LoadState.Loading
            val isError = it.refresh is LoadState.Error || it.mediator?.refresh is LoadState.Error

            if (!isRefresh && !isError) {
                showSuccess()
            } else if (isRefresh) {
                showLoading()
            } else if (isError) {
                if (movieAdapter.itemCount == 0) {
                    showError(getString(R.string.load_state_error))
                } else {
                    showSuccess()
                }
            }

            binding?.apply {
                swipeRefreshLayoutMovies.isRefreshing =
                    isRefresh && swipeRefreshLayoutMovies.isRefreshing
            }
        }

        binding?.apply {
            rvMovies.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = movieAdapter
                    .withLoadStateFooter(LoadStateAdapter {
                        movieAdapter.retry()
                    })
                setHasFixedSize(true)
            }
            swipeRefreshLayoutMovies.setOnRefreshListener {
                movieAdapter.refresh()
            }
            btnRetry.setOnClickListener {
                movieAdapter.retry()
            }
        }

        observe()
        viewModel.loadList()
    }

    @ExperimentalPagingApi
    private fun observe() {
        viewModel.list().observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                movieAdapter.submitData(it)
            }
        }
    }

    private fun showSuccess() {
        binding?.apply {
            progressBar.isVisible = false
            groupError.isVisible = false
            rvMovies.isVisible = true
        }
    }

    private fun showLoading() {
        binding?.apply {
            progressBar.isVisible = true
            groupError.isVisible = false
            rvMovies.isVisible = false
        }
    }

    private fun showError(message: String) {
        binding?.apply {
            progressBar.isVisible = false
            groupError.isVisible = true
            rvMovies.isVisible = false

            tvMessage.text = message
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        fun newInstance(): MoviesFragment = MoviesFragment()
    }

}