package com.kido1611.dicoding.moviecatalogue.fragment.tvs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kido1611.dicoding.moviecatalogue.databinding.TvsFragmentBinding
import com.kido1611.dicoding.moviecatalogue.fragment.movies.MovieListAdapter

class TvsFragment : Fragment() {

    companion object {
        fun newInstance(): TvsFragment = TvsFragment()
    }

    private lateinit var viewModel: TvsViewModel
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
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[TvsViewModel::class.java]

        val tvs = viewModel.list()

        val movieAdapter = MovieListAdapter()
        movieAdapter.setMovieList(tvs)

        binding.apply {
            rvTvs.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = movieAdapter
                setHasFixedSize(true)
            }
        }
    }

}