package com.kido1611.dicoding.moviecatalogue.fragment.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kido1611.dicoding.moviecatalogue.databinding.MoviesFragmentBinding

class MoviesFragment : Fragment() {

    companion object {
        fun newInstance(): MoviesFragment = MoviesFragment()
    }

    private lateinit var viewModel: MoviesViewModel
    private lateinit var binding: MoviesFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MoviesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MoviesViewModel::class.java]

        val movies = viewModel.list()

        val movieAdapter = MovieListAdapter()
        movieAdapter.setMovieList(movies)

        binding.apply {
            rvMovies.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = movieAdapter
                setHasFixedSize(true)
            }
        }
    }

}