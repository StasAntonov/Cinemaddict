package com.example.cinemaddict.component

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import androidx.core.view.forEach
import com.example.cinemaddict.R
import com.example.cinemaddict.ext.mainScope
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Job
import kotlin.math.max
import kotlin.math.min

class MovBottomNavigationView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defAttr: Int = 0
) : BottomNavigationView(context, attributeSet, defAttr) {

    private var bottomNavigationJob: Job? = null
    private val toUpMinBottomNavigationHeight: Int
        get() = max(this.height, 1)
    private val toUpMaxBottomNavigationHeight: Int
        get() = resources.getDimensionPixelSize(R.dimen.bottom_navigation_view_min_height)
    private val toDownMinBottomNavigationHeight: Int = 1
    private val toDownMaxBottomNavigationHeight: Int
        get() = min(
            this.height,
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
                            this@MovBottomNavigationView.menu.forEach { it.isEnabled = true }
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
                            this@MovBottomNavigationView.menu.forEach { it.isEnabled = false }
                        }
                        updateViewHeight(height)
                    }
                }

    private fun updateViewHeight(height: Int) {
        this.layoutParams?.height = height
        this.requestLayout()
    }

    fun showBar() {
        bottomNavigationJob?.cancel()
        bottomNavigationJob = mainScope {
            toUpAnimator.start()
        }
    }

    fun hideBar() {
        bottomNavigationJob?.cancel()
        bottomNavigationJob = mainScope {
            toDownAnimator.start()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        bottomNavigationJob?.cancel()
        toUpAnimator.removeAllUpdateListeners()
        toDownAnimator.removeAllUpdateListeners()
    }

    private companion object {
        const val BOTTOM_NAVIGATION_ANIMATION_DURATION: Long = 500
    }
}