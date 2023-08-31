package com.example.cinemaddict.ui.base

import android.view.LayoutInflater
import androidx.databinding.ViewDataBinding
import com.example.cinemaddict.R
import com.example.cinemaddict.component.ProgressView

abstract class BaseUiActivity<T : ViewDataBinding>(
    bindingInflater: (LayoutInflater) -> T
) : BaseActivity<T>(bindingInflater),
    ProgressView.Listener {

    private val progress: ProgressView? by lazy {
        binding.root.findViewById(R.id.progress)
    }

    override fun showLoader() {
        progress?.showProgress() ?: progressViewError()
    }

    override fun hideLoader() {
        progress?.hideProgress() ?: progressViewError()
    }

    private fun progressViewError(): Nothing =
        error("ProgressView has not been added to the screen. Make sure you added progress to your activity and set android:id=\"@+id/progress\"")
}