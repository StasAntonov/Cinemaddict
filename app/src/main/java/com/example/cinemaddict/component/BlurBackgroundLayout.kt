package com.example.cinemaddict.component

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.allViews
import androidx.core.view.doOnNextLayout
import androidx.core.view.drawToBitmap
import com.example.cinemaddict.R
import jp.wasabeef.glide.transformations.internal.FastBlur
import jp.wasabeef.glide.transformations.internal.RSBlur
import kotlin.math.cos
import kotlin.math.sin

class BlurBackgroundLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var bitmap: Bitmap? = null
    private var previousBitmap: Bitmap? = null
    private var radius = 0f
    private var blur = 0
    private var angleInDegrees = 0
    private var strokeWidthGradientLine = 0f

    private val halfStrokeWidth: Float
        get() = strokeWidthGradientLine / 2

    private val radiusCornerLine: Float
        get() = radius - halfStrokeWidth

    @ColorRes
    private var startColor = R.color.transparent

    @ColorRes
    private var endColor = R.color.transparent

    @ColorRes
    private var overlayColor: Int = R.color.primary_text

    private var paint = Paint()

    private var overlayPaint = Paint()

    private val gradient
        get() = getGradientAngle()

    private val viewShape by lazy {
        RectF(0f, 0f, width.toFloat(), height.toFloat())
    }

    private val roundedRect by lazy {
        RectF(
            halfStrokeWidth, halfStrokeWidth,
            width.toFloat() - halfStrokeWidth, height.toFloat() - halfStrokeWidth
        )
    }

    private val blurView: View? by lazy { rootView.allViews.find { it == this } }

    private val viewsUnderBlur: List<View> by lazy {
        rootView.allViews.takeWhile { it != this }
            .filter { it !is ViewGroup && viewsOverlap(blurView, it) }
            .toList()
    }

    init {
        context.obtainStyledAttributes(attrs, R.styleable.BlurBackgroundLayout, defStyleAttr, 0)
            .let {
                radius = it.getDimension(
                    R.styleable.BlurBackgroundLayout_cornerRadiusView,
                    radius
                )
                overlayColor = it.getResourceId(
                    R.styleable.BlurBackgroundLayout_backgroundColorOverlay,
                    overlayColor
                )
                startColor = it.getResourceId(
                    R.styleable.BlurBackgroundLayout_startColorGradient,
                    startColor
                )
                endColor = it.getResourceId(
                    R.styleable.BlurBackgroundLayout_endColorGradient,
                    endColor
                )
                strokeWidthGradientLine = it.getDimension(
                    R.styleable.BlurBackgroundLayout_strokeWidth,
                    strokeWidthGradientLine
                )
                angleInDegrees = it.getInt(
                    R.styleable.BlurBackgroundLayout_angleGradient,
                    angleInDegrees
                )
                blur = it.getInt(
                    R.styleable.BlurBackgroundLayout_blur, blur
                )
                it.recycle()
            }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        addOnDrawListener()
        updateBackgroundFromView()
    }

    private fun addOnDrawListener() {
        viewsUnderBlur.forEach {
            it.doOnNextLayout { view ->
                if (it.drawToBitmap() != view.drawToBitmap())
                    updateBackgroundFromView()
            }
        }
    }

    private fun viewsOverlap(view1: View?, view2: View?): Boolean {
        val rect1 = Rect()
        val rect2 = Rect()

        view1?.getGlobalVisibleRect(rect1) ?: return false
        view2?.getGlobalVisibleRect(rect2) ?: return false

        return rect1.intersect(rect2)
    }

    private fun updateBackgroundFromView() {
        val location = IntArray(2)
        getLocationOnScreen(location)
        val viewX = location[0]
        val viewY = location[1]

        val croppedBitmap = Bitmap.createBitmap(getScreenshot(), viewX, viewY, width, height)

        bitmap = if (blur > 0) {
            applyBlur(croppedBitmap)
        } else {
            croppedBitmap
        }
        bitmap = createRoundedBitmap(bitmap)

        if (bitmap?.equals(previousBitmap) == false) {
            bitmap?.let {
                background = BitmapDrawable(resources, it)
            }
            previousBitmap = bitmap
        }
    }

    private fun getScreenshot(): Bitmap {
        val rootView = rootView

        val screenshot =
            Bitmap.createBitmap(rootView.width, rootView.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(screenshot)

        viewsUnderBlur.forEach { view ->
            val viewLocation = IntArray(2)
            view.getLocationOnScreen(viewLocation)
            canvas.save()
            canvas.translate(viewLocation.first().toFloat(), viewLocation.last().toFloat())
            view.draw(canvas)
            canvas.restore()
        }

        return screenshot
    }

    private fun getGradientAngle(): LinearGradient {
        val colors = intArrayOf(
            ContextCompat.getColor(context, startColor),
            ContextCompat.getColor(context, endColor)
        )

        val centerX = width / 2f
        val centerY = height / 2f

        val angleInRadians = Math.toRadians(angleInDegrees.toDouble())
        val x0 = centerX - cos(angleInRadians) * width / 2
        val y0 = centerY - sin(angleInRadians) * height / 2
        val x1 = centerX + cos(angleInRadians) * width / 2
        val y1 = centerY + sin(angleInRadians) * height / 2

        return LinearGradient(
            x0.toFloat(), y0.toFloat(),
            x1.toFloat(), y1.toFloat(),
            colors, null, Shader.TileMode.CLAMP
        )
    }

    private fun createGradientLine(): Paint {
        return paint.apply {
            shader = gradient
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeWidth = strokeWidthGradientLine
        }
    }

    private fun applyBlur(sourceBitmap: Bitmap): Bitmap {
        return try {
            RSBlur.blur(context, sourceBitmap, blur)
        } catch (e: Exception) {
            FastBlur.blur(sourceBitmap, blur, true)
        }
    }

    private fun createRoundedBitmap(sourceBitmap: Bitmap?): Bitmap? {
        sourceBitmap ?: return null

        val output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)

        val paint = Paint()
        paint.isAntiAlias = true
        canvas.drawRoundRect(viewShape, radius, radius, paint)

        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(sourceBitmap, 0f, 0f, paint)

        overlayPaint.apply {
            isAntiAlias = true
            color = ContextCompat.getColor(context, overlayColor)
        }
        canvas.drawRoundRect(viewShape, radius, radius, overlayPaint)

        canvas.drawRoundRect(roundedRect, radiusCornerLine, radiusCornerLine, createGradientLine())

        return output
    }
}