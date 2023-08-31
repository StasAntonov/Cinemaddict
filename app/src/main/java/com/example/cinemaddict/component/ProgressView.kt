package com.example.cinemaddict.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.cinemaddict.databinding.ViewProgressBinding
import com.example.cinemaddict.ext.toGone
import com.example.cinemaddict.ext.toVisible

class ProgressView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defAttr: Int = 0
) : FrameLayout(context, attributeSet, defAttr) {

    private val binding: ViewProgressBinding =
        ViewProgressBinding.inflate(LayoutInflater.from(context), this, true)

    fun showProgress() {
        binding.lavAnim.playAnimation()
        binding.container.toVisible()
    }

    fun hideProgress() {
        binding.lavAnim.pauseAnimation()
        binding.container.toGone()
    }

    interface Listener {
        fun showLoader()
        fun hideLoader()
    }
}