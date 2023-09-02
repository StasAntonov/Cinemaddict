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

    private var toUpAnimator: ValueAnimator

    private var toDownAnimator: ValueAnimator

    private val viewHeight: Int = resources.getDimensionPixelSize(R.dimen.view_info_height)

    private var minViewHeight: Int = resources.getDimensionPixelSize(R.dimen.view_info_min_height)
    private var heightChangingDuration = HEIGHT_CHANGING_DURATION
    private var animationDelay = ANIMATION_DELAY

    init {
        context.obtainStyledAttributes(attributeSet, R.styleable.InfoBarView, defAttr, 0).let {
            minViewHeight = it.getResourceId(R.styleable.InfoBarView_min_view_height, minViewHeight)
            heightChangingDuration = it.getInt(
                R.styleable.InfoBarView_height_changing_duration,
                heightChangingDuration.toInt()
            ).toLong()
            animationDelay =
                it.getInt(R.styleable.InfoBarView_min_view_height, animationDelay.toInt()).toLong()

            toUpAnimator = ValueAnimator.ofInt(minViewHeight, viewHeight).apply {
                duration = heightChangingDuration
                addUpdateListener { value ->
                    updateViewHeight(value.animatedValue as Int)
                }
            }

            toDownAnimator = ValueAnimator.ofInt(viewHeight, minViewHeight).apply {
                duration = heightChangingDuration
                addUpdateListener { value ->
                    updateViewHeight(value.animatedValue as Int)
                }
            }

            it.recycle()
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

            if (container.height == minViewHeight) {
                toUpAnimator.start()
            }

            if (isShowAlways) return@launch
            delay(animationDelay)

            toDownAnimator.start()
        }
    }

    private fun updateViewHeight(height: Int) = with(binding) {
        container.layoutParams.height = height
        container.requestLayout()
    }

    private companion object {
        const val HEIGHT_CHANGING_DURATION = 300L
        const val ANIMATION_DELAY = 4000L
    }

    interface Listener {
        fun showMessage(message: String, isShowAlways: Boolean)
        fun showErrorMessage(message: String, isShowAlways: Boolean)
        fun showSuccessMessage(message: String, isShowAlways: Boolean)
    }
}