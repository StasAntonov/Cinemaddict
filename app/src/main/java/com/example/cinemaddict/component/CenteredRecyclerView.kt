package com.example.cinemaddict.component

import android.content.Context
import android.util.AttributeSet
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemaddict.R
import com.example.cinemaddict.util.CarouselLayoutManager

class CenteredRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defAttrs: Int = 0
) : RecyclerView(context, attrs, defAttrs) {

    /**
     * view compression starts when the left side of the view reaches this position
     */
    private var startShrink: Float = Float.MIN_VALUE

    /**
     * view compression ends when the right side of the view reaches this position
     */
    private var endShrink: Float = 0f

    /**
     * how much to shrink a view relative to its original size
     * the original size is taken as 1
     */
    private var shrinkRatio: Float = 0.75f
    private var isCenterLastItem: Boolean = true

    private var isScrolled = false
    private val snapHelper = LinearSnapHelper()

    init {
        context.obtainStyledAttributes(attrs, R.styleable.CenteredRecyclerView, defAttrs, 0).let {
            startShrink = it.getDimension(R.styleable.CenteredRecyclerView_startShrink, startShrink)
            endShrink = it.getDimension(R.styleable.CenteredRecyclerView_endShrink, endShrink)
            shrinkRatio = it.getFloat(R.styleable.CenteredRecyclerView_shrinkRatio, shrinkRatio)
            isCenterLastItem =
                it.getBoolean(R.styleable.CenteredRecyclerView_isCenterLastItem, isCenterLastItem)

            it.recycle()
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)

        (layoutManager as? CarouselLayoutManager)?.apply {
            if (!isInitialized) {
                val recyclerWidth = r - l
                getChildAt(0)?.let { child ->
                    if (startShrink == Float.MIN_VALUE) {
                        startShrink = recyclerWidth / 2f - child.width / 2
                    }
                    if (isCenterLastItem) {
                        updatePadding(right = recyclerWidth / 2 - child.width / 2)
                    }
                    setStartShrink(startShrink)
                    setEndShrink(endShrink)
                    setShrinkRatio(shrinkRatio)
                }
            }
        }
    }

    override fun onScrolled(dx: Int, dy: Int) {
        super.onScrolled(dx, dy)
        if (dx != 0 || dy != 0) {
            isScrolled = true
        }
    }

    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)
        if (state == SCROLL_STATE_IDLE && isScrolled) {
            isScrolled = false

            layoutManager?.let { lm ->
                snapHelper.findSnapView(lm)?.let { snapView ->
                    val snapDistance =
                        snapHelper.calculateDistanceToFinalSnap(lm, snapView)
                            ?: return
                    smoothScrollBy(
                        snapDistance[0] + (paddingStart - paddingEnd) / 2,
                        snapDistance[1]
                    )
                }
            }
        }
    }
}
