package com.kido1611.dicoding.moviecatalogue.fragment.bookmark_movies

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
class BookmarkMoviesFragment : Fragment() {

    companion object {
        fun newInstance() = BookmarkMoviesFragment()
    }

    private lateinit var binding: MoviesFragmentBinding
    private val viewModel: BookmarkMoviesViewModel by viewModels()

    private lateinit var movieAdapter: BookmarkPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MoviesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @ExperimentalPagingApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieAdapter = BookmarkPagingAdapter()
        movieAdapter.addLoadStateListener {
            val isRefresh =
                it.refresh is LoadState.Loading || it.mediator?.refresh is LoadState.Loading
            val isError = it.refresh is LoadState.Error || it.mediator?.refresh is LoadState.Error

            if (!isRefresh && !isError) {
                if (movieAdapter.itemCount == 0) {
                    showError(getString(R.string.list_empty))
                } else {
                    showSuccess()
                }
            } else if (isRefresh) {
                showLoading()
            } else if (isError) {
                if (movieAdapter.itemCount == 0) {
                    showError(getString(R.string.load_state_error))
                } else {
                    showSuccess()
                }
            }

            binding.swipeRefreshLayout.isRefreshing =
                isRefresh && binding.swipeRefreshLayout.isRefreshing
        }

        binding.apply {
            rvMovies.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = movieAdapter
                    .withLoadStateFooter(LoadStateAdapter {
                        movieAdapter.retry()
                    })
                setHasFixedSize(true)
            }
            swipeRefreshLayout.setOnRefreshListener {
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
        binding.apply {
            progressBar.isVisible = false
            groupError.isVisible = false
        }
    }

    private fun showLoading() {
        binding.apply {
            progressBar.isVisible = true
            groupError.isVisible = false
        }
    }

    private fun showError(message: String) {
        binding.apply {
            progressBar.isVisible = false
            groupError.isVisible = true

            tvMessage.text = message
        }
    }

}