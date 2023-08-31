package com.example.cinemaddict.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.ViewDataBinding
import com.example.cinemaddict.common.PullToRefreshListener
import com.example.cinemaddict.component.ProgressView

abstract class BaseUiFragment<T : ViewDataBinding>(
    bindingInflater: (LayoutInflater) -> T
) : BaseFragment<T>(bindingInflater) {

    private val progress: ProgressView.Listener by lazy { activity as ProgressView.Listener }
    private val pullToRefresh: PullToRefreshListener by lazy { activity as PullToRefreshListener }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pullToRefresh.setOnRefreshListener(::onRefresh)
    }

    fun showLoader() {
        progress.showLoader()
    }

    fun hideLoader() {
        progress.hideLoader()
    }

    open suspend fun onRefresh() {}

    override fun onStop() {
        super.onStop()
        progress.hideLoader()
    }
}