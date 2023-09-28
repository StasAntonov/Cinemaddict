package com.example.cinemaddict.ui.base

import android.view.LayoutInflater
import androidx.databinding.ViewDataBinding
import com.example.cinemaddict.R
import com.example.cinemaddict.common.BottomNavigationViewListener
import com.example.cinemaddict.component.InfoBarView
import com.example.cinemaddict.component.MovBottomNavigationView
import com.example.cinemaddict.component.ProgressView

abstract class BaseUiActivity<T : ViewDataBinding>(
    bindingInflater: (LayoutInflater) -> T
) : BaseActivity<T>(bindingInflater),
    ProgressView.Listener,
    InfoBarView.Listener,
    BottomNavigationViewListener {

    private val progress: ProgressView? by lazy {
        binding.root.findViewById(R.id.progress)
    }

    private val infoBar: InfoBarView? by lazy {
        binding.root.findViewById(R.id.info_bar)
    }

    private val bnvNavigation: MovBottomNavigationView? by lazy {
        binding.root.findViewById(R.id.bnv_navigation)
    }

    override fun showLoader() {
        progress?.showProgress() ?: progressViewError()
    }

    override fun hideLoader() {
        progress?.hideProgress() ?: progressViewError()
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
        bnvNavigation?.showBar() ?: navigationBarError()
    }

    override fun hideNavigationBar() {
        bnvNavigation?.hideBar() ?: navigationBarError()
    }

    private fun progressViewError(): Nothing =
        error("ProgressView has not been added to the screen. Make sure you added progress to your activity and set android:id=\"@+id/progress\"")

    private fun infoBarError(): Nothing =
        error("InfoBar has not been added to the screen. Make sure you added progress to your activity and set android:id=\"@+id/info_bar\"")

    private fun navigationBarError(): Nothing =
        error("BottomNavigationView has not been added to the screen. Make sure you added progress to your activity and set android:id=\"@+id/bnv_navigation\"")
}