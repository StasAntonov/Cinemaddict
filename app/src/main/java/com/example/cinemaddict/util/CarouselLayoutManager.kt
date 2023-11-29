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

    /**
     * view compression starts when the left side of the view reaches this position
     */
    private val startShrink: Float = 47f    //todo

    /**
     * view compression ends when the right side of the view reaches this position
     */
    private val endShrink: Float = 65f  //todo

    private val shrinkRatio = 1 - 0.869f    // todo
    private val midpoint: Float
        get() = width / 2f

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
                    val shrinkPerPixel = shrinkRatio / shrinkDistance

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
}