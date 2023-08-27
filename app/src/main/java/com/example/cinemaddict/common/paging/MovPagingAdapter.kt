package com.example.cinemaddict.common.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class MovPagingAdapter<T : MovPagingViewData, VDB : ViewDataBinding>(
    private val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> VDB
) : PagingDataAdapter<T, MovPagingAdapter.MovViewHolder>(MovDiffUtil()) {

    class MovDiffUtil<T : MovPagingViewData> : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
            oldItem.hashCode() == newItem.hashCode()
    }

    class MovViewHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun <T : MovPagingViewData> bind(item: T) = with(binding) {
//            binding.setVariable(BR.viewData, item) todo needed for use by dataBinding in xml
            executePendingBindings()
        }
    }

    override fun onBindViewHolder(holder: MovViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.bind(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovViewHolder {
        val binding = bindingInflater(LayoutInflater.from(parent.context), parent, false)
        return MovViewHolder(binding)
    }
}