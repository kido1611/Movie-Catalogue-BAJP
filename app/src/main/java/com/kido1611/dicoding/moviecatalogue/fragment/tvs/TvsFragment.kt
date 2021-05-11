package com.kido1611.dicoding.moviecatalogue.fragment.tvs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kido1611.dicoding.moviecatalogue.data.source.DiscoverUiState
import com.kido1611.dicoding.moviecatalogue.databinding.TvsFragmentBinding
import com.kido1611.dicoding.moviecatalogue.fragment.movies.MovieListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvsFragment : Fragment() {

    companion object {
        fun newInstance(): TvsFragment = TvsFragment()
    }

    private val viewModel: TvsViewModel by viewModels()
    private lateinit var binding: TvsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TvsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieAdapter = MovieListAdapter()

        binding.apply {
            rvTvs.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = movieAdapter
                setHasFixedSize(true)
            }
        }

        viewModel.list.observe(viewLifecycleOwner) {
            when (it) {
                is DiscoverUiState.Error -> {
                    showError(it.message)
                }
                is DiscoverUiState.Loading -> {
                    showLoading()
                }
                is DiscoverUiState.Success -> {
                    showSuccess()

                    movieAdapter.setMovieList(it.list)
                    movieAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun showSuccess() {
        binding.apply {
            progressBar.isVisible = false
            groupError.isVisible = false
            rvTvs.isVisible = true
        }
    }

    private fun showLoading() {
        binding.apply {
            progressBar.isVisible = true
            groupError.isVisible = false
            rvTvs.isVisible = false
        }
    }

    private fun showError(message: String) {
        binding.apply {
            progressBar.isVisible = false
            groupError.isVisible = true
            rvTvs.isVisible = false

            tvMessage.text = message
        }
    }
}