package com.example.cinemaddict.component.infobar

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
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

    private val viewHeight: Int = resources.getDimensionPixelSize(R.dimen.view_info_height)

    private val toUpAnimator: ValueAnimator by lazy {
        ValueAnimator.ofInt(MIN_VIEW_HEIGHT, viewHeight).apply {
            duration = HEIGHT_CHANGING_DURATION
            addUpdateListener { value ->
                updateViewHeight(value.animatedValue as Int)
            }
        }
    }

    private val toDownAnimator: ValueAnimator by lazy {
        ValueAnimator.ofInt(viewHeight, MIN_VIEW_HEIGHT).apply {
            duration = HEIGHT_CHANGING_DURATION
            addUpdateListener { value ->
                updateViewHeight(value.animatedValue as Int)
            }
        }
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

            if (container.height == MIN_VIEW_HEIGHT) {
                toUpAnimator.start()
            }

            if (isShowAlways) return@launch
            delay(ANIMATION_DELAY)

            toDownAnimator.start()
        }
    }

    private fun updateViewHeight(height: Int) = with(binding) {
        container.layoutParams.height = height
        container.requestLayout()
    }

    private companion object {
        const val MIN_VIEW_HEIGHT = 0
        const val HEIGHT_CHANGING_DURATION = 300L
        const val ANIMATION_DELAY = 4000L
    }

    interface Listener {
        fun showMessage(message: String, isShowAlways: Boolean)
        fun showErrorMessage(message: String, isShowAlways: Boolean)
        fun showSuccessMessage(message: String, isShowAlways: Boolean)
    }
}