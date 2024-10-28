package com.shakiv.whatsappsample.presentation.adapterLoader

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shakiv.whatsappsample.databinding.LayoutLoadStateBinding

class MainLoadStateAdapter : LoadStateAdapter<MainLoadStateAdapter.LoadStateViewHolder>() {

    inner class LoadStateViewHolder(val binding: LayoutLoadStateBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.binding.progressBar.isVisible = loadState is LoadState.Loading
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding =
            LayoutLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadStateViewHolder(binding)
    }


}