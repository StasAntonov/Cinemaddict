package com.example.cinemaddict.util

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LoadState
import com.example.cinemaddict.common.paging.MovPagingAdapter
import com.example.cinemaddict.common.paging.MovPagingData

class LoadStateListener<T : MovPagingData, VDB : ViewDataBinding>(
    private val adapter: MovPagingAdapter<T, VDB>,
    private val toastCallback: (String) -> Unit
) {

    private var _isLoad = MutableLiveData(false)
    val isLoad: LiveData<Boolean>
        get() = _isLoad

    init {
        loadState()
    }

    private fun loadState() {
        adapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading ||
                loadState.append is LoadState.Loading
            ) {
                _isLoad.postValue(true)
            } else {
                _isLoad.postValue(false)
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                errorState?.let {
                    toastCallback(it.error.toString())
                }
            }
        }
    }
}
