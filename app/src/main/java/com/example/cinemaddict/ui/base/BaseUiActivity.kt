package com.example.cinemaddict.ui.base

import android.animation.ValueAnimator
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import com.example.cinemaddict.R
import com.example.cinemaddict.common.BottomNavigationViewListener
import com.example.cinemaddict.common.PullToRefreshCallback
import com.example.cinemaddict.common.PullToRefreshListener
import com.example.cinemaddict.component.InfoBarView
import com.example.cinemaddict.component.ProgressView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.simform.refresh.SSPullToRefreshLayout
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.math.max
import kotlin.math.min

abstract class BaseUiActivity<T : ViewDataBinding>(
    bindingInflater: (LayoutInflater) -> T
) : BaseActivity<T>(bindingInflater),
    ProgressView.Listener,
    PullToRefreshListener,
    InfoBarView.Listener,
    BottomNavigationViewListener {

    private val progress: ProgressView? by lazy {
        binding.root.findViewById(R.id.progress)
    }

    private val pullToRefresh: SSPullToRefreshLayout? by lazy {
        binding.root.findViewById(R.id.pull_to_refresh)
    }

    private val infoBar: InfoBarView? by lazy {
        binding.root.findViewById(R.id.info_bar)
    }

    private val bnvNavigation: BottomNavigationView? by lazy {
        binding.root.findViewById(R.id.bnv_navigation)
    }

    private var bottomNavigationJob: Job? = null
    private val toUpMinBottomNavigationHeight: Int
        get() = max(
            bnvNavigation?.height ?: 1, 1
        )
    private val toUpMaxBottomNavigationHeight: Int
        get() = resources.getDimensionPixelSize(R.dimen.bottom_navigation_view_min_height)
    private val toDownMinBottomNavigationHeight: Int = 1
    private val toDownMaxBottomNavigationHeight: Int
        get() = min(
            bnvNavigation?.height
                ?: resources.getDimensionPixelSize(R.dimen.bottom_navigation_view_min_height),
            resources.getDimensionPixelSize(R.dimen.bottom_navigation_view_min_height)
        )

    private val toUpAnimator: ValueAnimator
        get() =
            ValueAnimator.ofInt(toUpMinBottomNavigationHeight, toUpMaxBottomNavigationHeight)
                .apply {
                    duration = BOTTOM_NAVIGATION_ANIMATION_DURATION
                    addUpdateListener { value ->
                        val height = value.animatedValue as Int
                        if (height == toUpMinBottomNavigationHeight) {
                            bnvNavigation?.menu?.forEach { it.isEnabled = true }
                        }
                        updateViewHeight(height)
                    }
                }

    private val toDownAnimator: ValueAnimator
        get() =
            ValueAnimator.ofInt(toDownMaxBottomNavigationHeight, toDownMinBottomNavigationHeight)
                .apply {
                    duration = BOTTOM_NAVIGATION_ANIMATION_DURATION
                    addUpdateListener { value ->
                        val height = value.animatedValue as Int
                        if (height == toDownMaxBottomNavigationHeight) {
                            bnvNavigation?.menu?.forEach { it.isEnabled = false }
                        }
                        updateViewHeight(height)
                    }
                }

    private fun updateViewHeight(height: Int) {
        bnvNavigation?.let {
            it.layoutParams?.height = height
            it.requestLayout()
        } ?: navigationBarError()
    }

    override fun initViews() {
        super.initViews()
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

    override fun showMessage(message: String, isShowAlways: Boolean) {
        infoBar?.showDefaultMessage(message, isShowAlways) ?: infoBarError()
    }

    override fun showErrorMessage(message: String, isShowAlways: Boolean) {
        infoBar?.showErrorMessage(message, isShowAlways) ?: infoBarError()
    }

    override fun showSuccessMessage(message: String, isShowAlways: Boolean) {
        infoBar?.showSuccessMessage(message, isShowAlways) ?: infoBarError()
    }

    override fun showNavigationBar() {
        bottomNavigationJob?.cancel()
        bottomNavigationJob = lifecycleScope.launch {
            toUpAnimator.start()
        }
    }

    override fun hideNavigationBar() {
        bottomNavigationJob?.cancel()
        bottomNavigationJob = lifecycleScope.launch {
            toDownAnimator.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        bottomNavigationJob?.cancel()
        toUpAnimator.removeAllUpdateListeners()
        toDownAnimator.removeAllUpdateListeners()
    }

    private fun progressViewError(): Nothing =
        error("ProgressView has not been added to the screen. Make sure you added progress to your activity and set android:id=\"@+id/progress\"")

    private fun pullToRefreshError(): Nothing =
        error("SSPullToRefreshLayout has not been added to the screen. Make sure you added progress to your activity and set android:id=\"@+id/pull_to_refresh\"")

    private fun infoBarError(): Nothing =
        error("InfoBar has not been added to the screen. Make sure you added progress to your activity and set android:id=\"@+id/info_bar\"")

    private fun navigationBarError(): Nothing =
        error("BottomNavigationView has not been added to the screen. Make sure you added progress to your activity and set android:id=\"@+id/bnv_navigation\"")

    private companion object {
        const val BOTTOM_NAVIGATION_ANIMATION_DURATION: Long = 500
    }
}