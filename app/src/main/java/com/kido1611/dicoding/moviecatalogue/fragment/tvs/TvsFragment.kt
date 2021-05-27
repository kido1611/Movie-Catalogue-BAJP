package com.kido1611.dicoding.moviecatalogue.fragment.tvs

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
import com.kido1611.dicoding.moviecatalogue.databinding.TvsFragmentBinding
import com.kido1611.dicoding.moviecatalogue.fragment.movies.MoviePagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TvsFragment : Fragment() {

    private val viewModel: TvsViewModel by viewModels()
    private var binding: TvsFragmentBinding? = null
    private lateinit var movieAdapter: MoviePagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = TvsFragmentBinding.inflate(inflater, container, false)
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
                swipeRefreshLayoutTvs.isRefreshing =
                    isRefresh && swipeRefreshLayoutTvs.isRefreshing
            }
        }

        binding?.apply {
            rvTvs.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = movieAdapter
                    .withLoadStateFooter(LoadStateAdapter {
                        movieAdapter.retry()
                    })
                setHasFixedSize(true)
            }
            swipeRefreshLayoutTvs.setOnRefreshListener {
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
            rvTvs.isVisible = true
        }
    }

    private fun showLoading() {
        binding?.apply {
            progressBar.isVisible = true
            groupError.isVisible = false
            rvTvs.isVisible = false
        }
    }

    private fun showError(message: String) {
        binding?.apply {
            progressBar.isVisible = false
            groupError.isVisible = true
            rvTvs.isVisible = false

            tvMessage.text = message
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        fun newInstance(): TvsFragment = TvsFragment()
    }

}