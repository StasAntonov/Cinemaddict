package com.example.cinemaddict.component

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView

class CenteredRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defAttrs: Int = 0
) : RecyclerView(context, attrs, defAttrs) {

    private var isScrolled = false
    private val snapHelper = LinearSnapHelper()

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
