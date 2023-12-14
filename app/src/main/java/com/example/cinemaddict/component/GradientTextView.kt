package com.example.cinemaddict.component

import android.content.Context
import android.graphics.LinearGradient
import android.graphics.Shader
import android.util.AttributeSet
import androidx.annotation.ColorRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.example.cinemaddict.R

class GradientTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    @ColorRes
    private var startColor: Int = R.color.primary_text

    @ColorRes
    private var endColor: Int = R.color.primary_text

    private var useGradient: Boolean = false

    private val linearGradient: LinearGradient
        get() = LinearGradient(
            0f,
            0f,
            width.toFloat(),
            height.toFloat(),
            ContextCompat.getColor(context, startColor),
            ContextCompat.getColor(context, endColor),
            Shader.TileMode.CLAMP
        )


    init {
        context.obtainStyledAttributes(attrs, R.styleable.GradientTextView, defStyleAttr, 0).let {
            startColor = it.getResourceId(R.styleable.GradientTextView_startColor, startColor)
            endColor = it.getResourceId(R.styleable.GradientTextView_endColor, endColor)
            useGradient = it.getBoolean(R.styleable.GradientTextView_useGradient, useGradient)
            it.recycle()
        }
        if (useGradient) {
            enableGradient()
        }
    }

    fun enableGradient() {
        useGradient = true
        applyGradient()
    }

    fun disableGradient() {
        useGradient = false
        paint.shader = null
        setTextColor(ContextCompat.getColor(context, R.color.primary_text))
        invalidate()
    }

    private fun applyGradient() {
        if (useGradient) {
            paint.shader = linearGradient
        }
        invalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (useGradient) {
            applyGradient()
        }
    }
}