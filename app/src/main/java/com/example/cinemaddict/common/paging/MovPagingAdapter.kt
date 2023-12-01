package com.example.cinemaddict.common.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemaddict.BR

typealias MovPagingClickListener = (Int) -> Unit

data class MovPagingDataWrapper<T : MovPagingData>(
    val page: Int,
    val results: List<T>,
    val totalPages: Int,
    val totalResults: Int
)

abstract class MovPagingData(
    val id: Int = -1,
    var position: Int = -1,
    var onClickListener: MovPagingClickListener? = null
)

class MovPagingAdapter<T : MovPagingData, VDB : ViewDataBinding>(
    private val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> VDB,
    private val onClickListener: MovPagingClickListener? = null
) : PagingDataAdapter<T, MovPagingAdapter.MovViewHolder>(MovDiffUtil()) {

    class MovDiffUtil<T : MovPagingData> : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
            oldItem.hashCode() == newItem.hashCode()
    }

    class MovViewHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun <T : MovPagingData> bind(item: T) = with(binding) {
            binding.setVariable(BR.viewData, item)
            executePendingBindings()
        }
    }

    override fun onBindViewHolder(holder: MovViewHolder, position: Int) {
        getItem(position)?.let { item ->
            item.position = position
            item.onClickListener = onClickListener
            holder.bind(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovViewHolder {
        val binding = bindingInflater(LayoutInflater.from(parent.context), parent, false)
        return MovViewHolder(binding)
    }

    fun getItemByPosition(position: Int): T? = getItem(position)
}