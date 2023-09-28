package com.example.cinemaddict.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.example.cinemaddict.R
import com.example.cinemaddict.common.BottomNavigationViewListener
import com.example.cinemaddict.component.InfoBarView
import com.example.cinemaddict.component.ProgressView
import com.example.cinemaddict.ui.discover.BaseViewModel
import com.simform.refresh.SSPullToRefreshLayout
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

typealias PullToRefreshCallback = suspend () -> Unit

abstract class BaseUiFragment<T : ViewDataBinding>(
    bindingInflater: (LayoutInflater) -> T
) : BaseFragment<T>(bindingInflater) {

    private val progress: ProgressView.Listener? by lazy { activity as? ProgressView.Listener }
    private var pullToRefresh: SSPullToRefreshLayout? = null
    private val infoBar: InfoBarView.Listener? by lazy { activity as? InfoBarView.Listener }
    private val bnvNavigation: BottomNavigationViewListener? by lazy { activity as? BottomNavigationViewListener }

    protected open val viewModel: BaseViewModel? = null

    override fun initViews() {
        super.initViews()

        pullToRefresh = binding.root.findViewById(R.id.pull_to_refresh)
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

    override fun initObservers() {
        super.initObservers()
        viewModel?.isRefresh?.observe(viewLifecycleOwner) {
            pullToRefresh?.setRefreshing(it)
            pullToRefresh?.isEnabled = !it
        }
    }

    fun showLoader() {
        progress?.showLoader()
    }

    fun hideLoader() {
        progress?.hideLoader()
    }

    fun onRefresh(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        listener: PullToRefreshCallback
    ) {
        pullToRefresh?.apply {
            isEnabled = viewModel?.isRefresh?.value?.not() ?: true
            setOnRefreshListener {
                viewModel?.isRefresh?.postValue(true)
                CoroutineScope(dispatcher).launch {
                    listener.invoke()
                    viewModel?.isRefresh?.postValue(false)
                }
            }
        } ?: pullToRefreshError()
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

    fun showBar() {
        bnvNavigation?.showNavigationBar()
    }

    fun hideBar() {
        bnvNavigation?.hideNavigationBar()
    }

    override fun onStop() {
        super.onStop()
        progress?.hideLoader()
        pullToRefresh?.setOnRefreshListener(null)
    }

    private fun pullToRefreshError(): Nothing =
        error("SSPullToRefreshLayout has not been added to the screen. Make sure you added progress to your activity and set android:id=\"@+id/pull_to_refresh\"")
}