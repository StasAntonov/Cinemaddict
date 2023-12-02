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

    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {
        return if (orientation == HORIZONTAL) {
            for (i in 0 until childCount) {
                getChildAt(i)?.let { child ->
                    val startShrinkMidpoint = midpoint - (child.width / 2 + startShrink)
                    val endShrinkMidpoint = midpoint + (child.width / 2 - endShrink)
                    val shrinkDistance = endShrinkMidpoint - startShrinkMidpoint
                    val shrinkPerPixel = (1 - shrinkRatio) / shrinkDistance

                    val itemMidpoint =
                        abs(((getDecoratedLeft(child) + getDecoratedRight(child)) / 2f) - midpoint)
                    if (itemMidpoint !in startShrinkMidpoint..endShrinkMidpoint) return@let
                    val scale =
                        1f.coerceAtMost(1 - (itemMidpoint - startShrinkMidpoint) * shrinkPerPixel)
                    child.scaleY = scale
                }
            }
            super.scrollHorizontallyBy(dx, recycler, state)
        } else {
            0
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