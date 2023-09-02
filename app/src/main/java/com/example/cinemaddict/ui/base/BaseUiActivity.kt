package com.example.cinemaddict.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import com.example.cinemaddict.R
import com.example.cinemaddict.common.PullToRefreshCallback
import com.example.cinemaddict.common.PullToRefreshListener
import com.example.cinemaddict.component.ProgressView
import com.simform.refresh.SSPullToRefreshLayout
import kotlinx.coroutines.launch

abstract class BaseUiActivity<T : ViewDataBinding>(
    bindingInflater: (LayoutInflater) -> T
) : BaseActivity<T>(bindingInflater),
    ProgressView.Listener,
    PullToRefreshListener {

    private val progress: ProgressView? by lazy {
        binding.root.findViewById(R.id.progress)
    }

    private val pullToRefresh: SSPullToRefreshLayout? by lazy {
        binding.root.findViewById(R.id.pull_to_refresh)
    }

    @CallSuper
    override fun initViews() {
        pullToRefresh?.apply {
            isEnabled = false
            setLottieAnimation("refresh_animation.json")
            setRefreshStyle(SSPullToRefreshLayout.RefreshStyle.FLOAT)
            setRepeatMode(SSPullToRefreshLayout.RepeatMode.REPEAT)
            setRepeatCount(SSPullToRefreshLayout.RepeatCount.INFINITE)
            setRefreshViewParams(
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    resources.getDimensionPixelSize(R.dimen.pull_to_refresh_height)
                )
            )
        }
    }

    override fun showLoader() {
        progress?.showProgress() ?: progressViewError()
    }

    override fun hideLoader() {
        progress?.hideProgress() ?: progressViewError()
    }

    override fun setOnRefreshListener(listener: PullToRefreshCallback?) {
        pullToRefresh?.let {
            it.isEnabled = listener != null
            it.setOnRefreshListener {
                lifecycleScope.launch {
                    listener?.invoke()
                    it.setRefreshing(false)
                }
            }
        } ?: pullToRefreshError()
    }

    private fun progressViewError(): Nothing =
        error("ProgressView has not been added to the screen. Make sure you added progress to your activity and set android:id=\"@+id/progress\"")

    private fun pullToRefreshError(): Nothing =
        error("SSPullToRefreshLayout has not been added to the screen. Make sure you added progress to your activity and set android:id=\"@+id/pull_to_refresh\"")
}