package com.example.cinemaddict.component

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.example.cinemaddict.R
import com.example.cinemaddict.databinding.ViewInfoBarBinding
import com.example.cinemaddict.ext.mainScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

class InfoBarView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defAttr: Int = 0
) : FrameLayout(context, attributeSet, defAttr) {

    private enum class UiState(@ColorRes val color: Int) {
        NONE(android.R.color.transparent),
        DEFAULT(R.color.ib_default),
        SUCCESS(R.color.ib_success),
        ERROR(R.color.ib_error)
    }

    private val binding: ViewInfoBarBinding =
        ViewInfoBarBinding.inflate(LayoutInflater.from(context), this, true)

    private var animationJob: Job? = null

    private var state: UiState = UiState.NONE

    private var toUpAnimator: ValueAnimator
    private var toDownAnimator: ValueAnimator
    private var colorAnimator: ValueAnimator

    private val viewHeight: Int = resources.getDimensionPixelSize(R.dimen.view_info_height)

    private var minViewHeight: Int = resources.getDimensionPixelSize(R.dimen.view_info_min_height)
    private var heightChangingDuration = HEIGHT_CHANGING_DURATION
    private var heightAnimationDelay = HEIGHT_ANIMATION_DELAY
    private var colorAnimationDelay = COLOR_ANIMATION_DELAY

    init {
        context.obtainStyledAttributes(attributeSet, R.styleable.InfoBarView, defAttr, 0).let {
            minViewHeight = it.getResourceId(R.styleable.InfoBarView_minViewHeight, minViewHeight)
            heightChangingDuration = it.getInt(
                R.styleable.InfoBarView_heightChangingDuration,
                heightChangingDuration.toInt()
            ).toLong()
            heightAnimationDelay =
                it.getInt(
                    R.styleable.InfoBarView_heightAnimationDelay,
                    heightAnimationDelay.toInt()
                ).toLong()
            colorAnimationDelay =
                it.getInt(R.styleable.InfoBarView_colorAnimationDelay, colorAnimationDelay.toInt())
                    .toLong()

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

            colorAnimator = ValueAnimator.ofObject(
                ArgbEvaluator(),
                ContextCompat.getColor(context, UiState.NONE.color),
                ContextCompat.getColor(context, UiState.NONE.color)
            ).apply {
                duration = colorAnimationDelay
                addUpdateListener { value ->
                    binding.container.setBackgroundColor(value.animatedValue as Int)
                }
            }

            it.recycle()
        }
    }

    fun showDefaultMessage(message: String, isShowAlways: Boolean = false) {
        showMessage(message, UiState.DEFAULT, isShowAlways)
    }

    fun showErrorMessage(message: String, isShowAlways: Boolean = false) {
        showMessage(message, UiState.ERROR, isShowAlways)
    }

    fun showSuccessMessage(message: String, isShowAlways: Boolean = false) {
        showMessage(message, UiState.SUCCESS, isShowAlways)
    }

    private fun showMessage(
        message: String,
        newState: UiState,
        isShowAlways: Boolean = true
    ) = with(binding) {
        animationJob?.cancel()
        animationJob = mainScope {
            if (state == UiState.NONE) {
                container.setBackgroundColor(ContextCompat.getColor(context, newState.color))
                tvText.text = message
            } else if (state != newState) {
                colorAnimator.setObjectValues(
                    ContextCompat.getColor(context, state.color),
                    ContextCompat.getColor(context, newState.color)
                )

                colorAnimator.start()
                tvText.text = message
            }
            state = newState

            if (container.height == minViewHeight) {
                toUpAnimator.start()
            }

            if (isShowAlways) return@mainScope
            delay(heightAnimationDelay)

            toDownAnimator.start()
        }
    }

    private fun updateViewHeight(height: Int) = with(binding) {
        container.layoutParams.height = height
        container.requestLayout()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        toUpAnimator.removeAllUpdateListeners()
        toDownAnimator.removeAllUpdateListeners()
        colorAnimator.removeAllUpdateListeners()
    }

    private companion object {
        const val HEIGHT_CHANGING_DURATION = 300L
        const val HEIGHT_ANIMATION_DELAY = 4000L
        const val COLOR_ANIMATION_DELAY = 350L
    }

    interface Informer {
        fun showMessage(message: String, isShowAlways: Boolean)
        fun showErrorMessage(message: String, isShowAlways: Boolean)
        fun showSuccessMessage(message: String, isShowAlways: Boolean)
    }
}