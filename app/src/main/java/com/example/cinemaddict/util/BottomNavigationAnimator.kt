package com.example.cinemaddict.util

import android.animation.ValueAnimator
import androidx.core.view.doOnDetach
import androidx.core.view.forEach
import com.example.cinemaddict.R
import com.example.cinemaddict.ext.mainScope
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Job
import kotlin.math.max
import kotlin.math.min

class BottomNavigationAnimator(
    private val bottomNavigation: BottomNavigationView
) {
    private var bottomNavigationJob: Job? = null
    private val toUpMinBottomNavigationHeight: Int
        get() = max(bottomNavigation.height, 1)
    private val toUpMaxBottomNavigationHeight: Int
        get() = bottomNavigation.context.resources.getDimensionPixelSize(R.dimen.bottom_navigation_view_min_height)
    private val toDownMinBottomNavigationHeight: Int = 1
    private val toDownMaxBottomNavigationHeight: Int
        get() = min(
            bottomNavigation.height,
            bottomNavigation.context.resources.getDimensionPixelSize(R.dimen.bottom_navigation_view_min_height)
        )

    private val toUpAnimator: ValueAnimator
        get() =
            ValueAnimator.ofInt(toUpMinBottomNavigationHeight, toUpMaxBottomNavigationHeight)
                .apply {
                    duration = BOTTOM_NAVIGATION_ANIMATION_DURATION
                    addUpdateListener { value ->
                        val height = value.animatedValue as Int
                        if (height == toUpMinBottomNavigationHeight) {
                            bottomNavigation.menu.forEach { it.isEnabled = true }
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
                            bottomNavigation.menu.forEach { it.isEnabled = false }
                        }
                        updateViewHeight(height)
                    }
                }

    private fun updateViewHeight(height: Int) {
        bottomNavigation.layoutParams?.height = height
        bottomNavigation.requestLayout()
    }

    init {
        bottomNavigation.doOnDetach {
            bottomNavigationJob?.cancel()
            toUpAnimator.removeAllUpdateListeners()
            toDownAnimator.removeAllUpdateListeners()
        }
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

    interface Animator {
        fun showNavigationBar()
        fun hideNavigationBar()
    }

    private companion object {
        const val BOTTOM_NAVIGATION_ANIMATION_DURATION: Long = 500
    }
}