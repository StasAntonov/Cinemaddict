package com.example.cinemaddict.component

import android.content.Context
import android.graphics.LinearGradient
import android.graphics.Shader
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.example.cinemaddict.R

class GradientTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private val gradientColors = intArrayOf(
        ContextCompat.getColor(context, R.color.gradient_start_color),
        ContextCompat.getColor(context, R.color.gradient_end_color)
    )

    override fun onLayout(
        changed: Boolean, left: Int, top: Int, right: Int,
        bottom: Int
    ) {
        super.onLayout(changed, left, top, right, bottom)

        if (changed) {
            paint.shader = LinearGradient(
                0f,
                0f,
                width.toFloat(),
                height.toFloat(),
                gradientColors[0],
                gradientColors[1],
                Shader.TileMode.CLAMP
            )
        }
    }
}