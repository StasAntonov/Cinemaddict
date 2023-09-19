package com.example.cinemaddict.component

import android.content.Context
import android.graphics.Color
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
    }

    private fun setShader() {
        paint.shader = linearGradient
    }

    override fun onLayout(
        changed: Boolean, left: Int, top: Int, right: Int,
        bottom: Int
    ) {
        super.onLayout(changed, left, top, right, bottom)

        if (useGradient && paint.shader != linearGradient) {
            setShader()
            paint.shader = linearGradient
        } else if (!useGradient) {
            setColor()
        }
    }

    fun setTextColorGradient() {
        useGradient = true
        setShader()
    }

    fun setColor() {
        setTextColor(Color.WHITE)
        useGradient = false
        paint.shader = null
    }
}