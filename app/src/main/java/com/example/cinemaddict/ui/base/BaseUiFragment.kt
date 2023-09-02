package com.example.cinemaddict.ui.base

import android.view.LayoutInflater
import androidx.databinding.ViewDataBinding
import com.example.cinemaddict.common.PullToRefreshCallback
import com.example.cinemaddict.common.PullToRefreshListener
import com.example.cinemaddict.component.ProgressView

abstract class BaseUiFragment<T : ViewDataBinding>(
    bindingInflater: (LayoutInflater) -> T
) : BaseFragment<T>(bindingInflater) {

    private val progress: ProgressView.Listener by lazy { activity as ProgressView.Listener }
    private val pullToRefresh: PullToRefreshListener by lazy { activity as PullToRefreshListener }

    fun showLoader() {
        progress.showLoader()
    }

    fun hideLoader() {
        progress.hideLoader()
    }

    fun onRefresh(listener: PullToRefreshCallback) {
        pullToRefresh.setOnRefreshListener(listener)
    }

    override fun onStop() {
        super.onStop()
        progress.hideLoader()
        pullToRefresh.setOnRefreshListener(null)
    }
}