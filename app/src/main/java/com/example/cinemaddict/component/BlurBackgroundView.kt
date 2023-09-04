package com.example.cinemaddict.component

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.example.cinemaddict.R
import jp.wasabeef.glide.transformations.internal.FastBlur
import jp.wasabeef.glide.transformations.internal.RSBlur
import kotlin.math.cos
import kotlin.math.sin

class BlurBackgroundView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var backgroundImage: Bitmap? = null
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

    init {
        context.obtainStyledAttributes(attrs, R.styleable.BlurBackgroundView, defStyleAttr, 0)
            .let {
                radius = it.getDimension(
                    R.styleable.BlurBackgroundView_cornerRadiusView,
                    radius
                )
                overlayColor = it.getResourceId(
                    R.styleable.BlurBackgroundView_backgroundColorOverlay,
                    overlayColor
                )
                startColor = it.getResourceId(
                    R.styleable.BlurBackgroundView_startColorGradient,
                    startColor
                )
                endColor = it.getResourceId(
                    R.styleable.BlurBackgroundView_endColorGradient,
                    endColor
                )
                strokeWidthGradientLine = it.getDimension(
                    R.styleable.BlurBackgroundView_strokeWidth,
                    strokeWidthGradientLine
                )
                angleInDegrees = it.getInt(
                    R.styleable.BlurBackgroundView_angleGradient,
                    angleInDegrees
                )
                blur = it.getInt(
                    R.styleable.BlurBackgroundView_blur, blur
                )
                it.recycle()
            }
        backgroundImage = (background as? BitmapDrawable)?.bitmap
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        updateBackgroundFromView()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        backgroundImage?.let {
            canvas?.let { c ->
                c.drawBitmap(it, 0f, 0f, null)
                createColorBackground(canvas)
            }
        }

        canvas?.apply {
            drawRoundRect(roundedRect, radiusCornerLine, radiusCornerLine, createGradientLine())
        }
    }

    fun updateBackgroundFromView() {
        val location = IntArray(2)
        getLocationOnScreen(location)
        val viewX = location[0]
        val viewY = location[1]

        val rootView = rootView

        val screenshot =
            Bitmap.createBitmap(rootView.width, rootView.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(screenshot)
        rootView.draw(canvas)

        val croppedBitmap = Bitmap.createBitmap(screenshot, viewX, viewY, width, height)

        backgroundImage = applyBlur(croppedBitmap)
        backgroundImage = createRoundedBitmap(backgroundImage)
    }


//    fun updateBackgroundFromView() {
//        val location = IntArray(2)
//        getLocationOnScreen(location)
//        val viewX = location[0]
//        val viewY = location[1]
//        val screenshot = Bitmap.createBitmap(rootView.width, rootView.height, Bitmap.Config.ARGB_8888)
//        val canvas = Canvas(screenshot)
//        rootView.draw(canvas)
//
//        val croppedBitmap = Bitmap.createBitmap(screenshot, viewX, viewY, width, height)
//        backgroundImage = applyBlur(croppedBitmap)
//
//        createColorBackground(canvas)
//
//        invalidate()
//    }

    private fun createColorBackground(canvas: Canvas) {
        overlayPaint.color = ContextCompat.getColor(context, overlayColor)
        canvas.drawRoundRect(viewShape, radius, radius, overlayPaint)
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

        return output
    }
}