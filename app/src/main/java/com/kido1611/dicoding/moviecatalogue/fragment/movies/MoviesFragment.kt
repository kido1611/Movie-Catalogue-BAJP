package com.kido1611.dicoding.moviecatalogue.fragment.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kido1611.dicoding.moviecatalogue.data.source.UIState
import com.kido1611.dicoding.moviecatalogue.databinding.MoviesFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    companion object {
        fun newInstance(): MoviesFragment = MoviesFragment()
    }

    private val viewModel: MoviesViewModel by viewModels()
    private lateinit var binding: MoviesFragmentBinding
    private lateinit var movieAdapter: MovieListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MoviesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieAdapter = MovieListAdapter()

        binding.apply {
            rvMovies.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = movieAdapter
                setHasFixedSize(true)
            }
            btnRetry.setOnClickListener {
                observe()
            }
        }

        observe()
    }

    private fun observe() {
        viewModel.list.removeObservers(viewLifecycleOwner)
        viewModel.list.observe(viewLifecycleOwner) {
            when (it) {
                is UIState.Error -> {
                    showError(it.message)
                }
                UIState.Loading -> {
                    showLoading()
                }
                is UIState.Success -> {
                    showSuccess()

                    movieAdapter.setMovieList(it.data)
                    movieAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun showSuccess() {
        binding.apply {
            progressBar.isVisible = false
            groupError.isVisible = false
            rvMovies.isVisible = true
        }
    }

    private fun showLoading() {
        binding.apply {
            progressBar.isVisible = true
            groupError.isVisible = false
            rvMovies.isVisible = false
        }
    }

    private fun showError(message: String) {
        binding.apply {
            progressBar.isVisible = false
            groupError.isVisible = true
            rvMovies.isVisible = false

            tvMessage.text = message
        }
    }

}