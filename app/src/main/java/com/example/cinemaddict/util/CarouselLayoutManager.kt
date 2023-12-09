package com.example.cinemaddict.util

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

class CarouselLayoutManager @JvmOverloads constructor(
    context: Context,
    @RecyclerView.Orientation orientation: Int = RecyclerView.HORIZONTAL,
    reverseLayout: Boolean = false
) : LinearLayoutManager(context, orientation, reverseLayout) {

    private var startShrink: Float = Float.MIN_VALUE
    private var endShrink: Float = Float.MIN_VALUE
    private var shrinkRatio: Float = 0.75f
    private val midpoint: Float
        get() = width / 2f

    var isInitialized: Boolean = false
        private set

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        super.onLayoutChildren(recycler, state)
        scrollHorizontallyBy(0, recycler, state)
    }

    override fun onLayoutCompleted(state: RecyclerView.State?) =
        super.onLayoutCompleted(state).also { if (orientation == HORIZONTAL) scaleChildren() }

    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State
    ) = super.scrollHorizontallyBy(dx, recycler, state).also {
        if (orientation == HORIZONTAL) scaleChildren()
    }

    private fun scaleChildren() {
        var translationXForward = 0f

        for (i in 0 until childCount) {
            getChildAt(i)?.let { child ->
                val startShrinkMidpoint = midpoint - (child.width / 2 + startShrink)
                val endShrinkMidpoint = midpoint + (child.width / 2 - endShrink)
                val shrinkDistance = endShrinkMidpoint - startShrinkMidpoint
                val shrinkPerPixel = (1 - shrinkRatio) / shrinkDistance

                val childMidpoint = (child.left + child.right) / 2f
                val distanceToCenter = abs(childMidpoint - midpoint)

                child.isActivated = distanceToCenter < startShrinkMidpoint

                val scale =
                    1f.coerceAtMost(1 - (distanceToCenter - startShrinkMidpoint) * shrinkPerPixel)

                child.scaleX = scale
                child.scaleY = scale

                val translationDirection = if (childMidpoint > midpoint) -1 else 1
                val translationXFromScale = translationDirection * child.width * (1 - scale) / 2f
                child.translationX = translationXFromScale + translationXForward

                translationXForward = 0f

                if (translationXFromScale > 0 && i >= 1) {
                    // Edit previous child
                    getChildAt(i - 1)!!.translationX += 2 * translationXFromScale

                } else if (translationXFromScale < 0) {
                    // Pass on to next child
                    translationXForward = 2 * translationXFromScale
                }
            }
        }
    }

    fun setStartShrink(startShrink: Float) {
        this.startShrink = startShrink
        isInitialized = true
    }

    fun setEndShrink(endShrink: Float) {
        this.endShrink = endShrink
        isInitialized = true
    }

    fun setShrinkRatio(shrinkRatio: Float) {
        this.shrinkRatio = shrinkRatio
        isInitialized = true
    }
}