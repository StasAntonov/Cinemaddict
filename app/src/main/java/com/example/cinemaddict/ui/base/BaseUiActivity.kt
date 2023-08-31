package com.example.cinemaddict.ui.base

import android.os.Bundle
import android.view.LayoutInflater
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pullToRefresh?.apply {
            setLottieAnimation("refresh_animation.json")
            setRefreshStyle(SSPullToRefreshLayout.RefreshStyle.NORMAL)
            setRepeatMode(SSPullToRefreshLayout.RepeatMode.REPEAT)
            setRepeatCount(SSPullToRefreshLayout.RepeatCount.INFINITE)
        }
    }

    override fun showLoader() {
        progress?.showProgress() ?: progressViewError()
    }

    override fun hideLoader() {
        progress?.hideProgress() ?: progressViewError()
    }

    override fun setOnRefreshListener(listener: PullToRefreshCallback) {
        pullToRefresh?.let {
            it.setOnRefreshListener {
                lifecycleScope.launch {
                    listener.invoke()
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