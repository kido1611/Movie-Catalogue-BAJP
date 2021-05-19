package com.kido1611.dicoding.moviecatalogue.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.kido1611.dicoding.moviecatalogue.databinding.LoadStateViewBinding

class LoadStateViewHolder(
    private val binding: LoadStateViewBinding,
    private val retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(loadState: LoadState) {
        binding.progressBar.isVisible = loadState is LoadState.Loading

        binding.tvMessage.isVisible = loadState is LoadState.Error
        binding.btnRetry.isVisible = loadState is LoadState.Error

        binding.btnRetry.setOnClickListener {
            retry.invoke()
        }

    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): LoadStateViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = LoadStateViewBinding.inflate(inflater, parent, false)

            return LoadStateViewHolder(binding, retry)
        }
    }
}