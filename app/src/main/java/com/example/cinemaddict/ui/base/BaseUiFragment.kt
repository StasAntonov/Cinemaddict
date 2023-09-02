package com.example.cinemaddict.ui.base

import android.view.LayoutInflater
import androidx.databinding.ViewDataBinding
import com.example.cinemaddict.common.PullToRefreshCallback
import com.example.cinemaddict.common.PullToRefreshListener
import com.example.cinemaddict.component.InfoBarView
import com.example.cinemaddict.component.ProgressView

abstract class BaseUiFragment<T : ViewDataBinding>(
    bindingInflater: (LayoutInflater) -> T
) : BaseFragment<T>(bindingInflater) {

    private val progress: ProgressView.Listener? by lazy { activity as? ProgressView.Listener }
    private val pullToRefresh: PullToRefreshListener? by lazy { activity as? PullToRefreshListener }
    private val infoBar: InfoBarView.Listener? by lazy { activity as? InfoBarView.Listener }

    fun showLoader() {
        progress?.showLoader()
    }

    fun hideLoader() {
        progress?.hideLoader()
    }

    fun onRefresh(listener: PullToRefreshCallback) {
        pullToRefresh?.setOnRefreshListener(listener)
    }

    fun showMessage(message: String, isShowAlways: Boolean = false) {
        infoBar?.showMessage(message, isShowAlways)
    }

    fun showErrorMessage(message: String, isShowAlways: Boolean = false) {
        infoBar?.showErrorMessage(message, isShowAlways)
    }

    fun showSuccessMessage(message: String, isShowAlways: Boolean = false) {
        infoBar?.showSuccessMessage(message, isShowAlways)
    }

    override fun onStop() {
        super.onStop()
        progress?.hideLoader()
        pullToRefresh?.setOnRefreshListener(null)
    }
}