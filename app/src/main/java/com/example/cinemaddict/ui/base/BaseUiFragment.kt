package com.example.cinemaddict.ui.base

import android.view.LayoutInflater
import androidx.databinding.ViewDataBinding
import com.example.cinemaddict.component.ProgressView

abstract class BaseUiFragment<T : ViewDataBinding>(
    bindingInflater: (LayoutInflater) -> T
) : BaseFragment<T>(bindingInflater) {

    protected val progress: ProgressView.Listener by lazy { activity as ProgressView.Listener }
}