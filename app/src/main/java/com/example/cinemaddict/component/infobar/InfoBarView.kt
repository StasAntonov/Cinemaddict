package com.example.cinemaddict.component.infobar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import com.example.cinemaddict.R
import com.example.cinemaddict.databinding.ViewInfoBarBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class InfoBarView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defAttr: Int = 0
) : FrameLayout(context, attributeSet, defAttr) {

    private val binding: ViewInfoBarBinding =
        ViewInfoBarBinding.inflate(LayoutInflater.from(context), this, true)

    private val toTopAnimation = AnimationUtils.loadAnimation(context, R.anim.info_bar_top)
    private val toDownAnimation = AnimationUtils.loadAnimation(context, R.anim.info_bar_down)

    init {
        toDownAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) = Unit
            override fun onAnimationRepeat(p0: Animation?) = Unit
            override fun onAnimationEnd(p0: Animation?) {
                binding.container.visibility = View.GONE
            }
        })
    }

    fun showDefaultMessage(message: String, isShowAlways: Boolean = false) {
        showMessage(message, R.color.ib_default, isShowAlways)
    }

    fun showErrorMessage(message: String, isShowAlways: Boolean = false) {
        showMessage(message, R.color.ib_error, isShowAlways)
    }

    fun showSuccessMessage(message: String, isShowAlways: Boolean = false) {
        showMessage(message, R.color.ib_success, isShowAlways)
    }

    private fun showMessage(
        message: String,
        @ColorRes background: Int,
        isShowAlways: Boolean = true
    ) = with(binding) {
        CoroutineScope(Dispatchers.Main).launch {
            InfoViewData(
                text = message,
                background = background
            ).also {
                viewData = it
            }

            container.visibility = View.VISIBLE
            container.startAnimation(toTopAnimation)

            if (isShowAlways) return@launch
            delay(ANIMATION_DELAY)

            container.startAnimation(toDownAnimation)
        }
    }

    private companion object {
        const val ANIMATION_DELAY = 6000L
    }

    interface Listener {
        fun showMessage(message: String, isShowAlways: Boolean)
        fun showErrorMessage(message: String, isShowAlways: Boolean)
        fun showSuccessMessage(message: String, isShowAlways: Boolean)
    }
}