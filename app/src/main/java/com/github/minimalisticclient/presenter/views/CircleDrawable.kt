package com.github.minimalisticclient.presenter.views

import android.graphics.*
import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import androidx.annotation.ColorInt


open class CircleDrawable(
    var iconDrawable: Drawable,
    var size: Float,
    @ColorInt private var colorBackground: Int = 0,
    @ColorInt var colorBadge: Int = 0,
    @ColorInt var colorBadgeStroke: Int = Color.WHITE,
    var badgeDrawable: Drawable? = null,
    private var badgeSize: Int = 10,
    var badgeStrokeWidth: Int = 2,
    var rotateSpeed: Int = 5
) : VectorDrawable() {
    private val backgroundPaint: Paint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }
    private val circlePaint: Paint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }
    private val circleStrokePaint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }
    private val backgroundPath = Path()
    private var rotateAngle = 0f
    private var rotate: Boolean = false
    private var shouldStopRotate = false
    private var doWhenRotateFinished: (() -> Unit)? = null

    private val rotateRect: Rect by lazy {
        Rect().apply {
            left = (-iconDrawable.intrinsicWidth).div(2)
            top = (-iconDrawable.intrinsicHeight).div(2)
            right = left + iconDrawable.intrinsicWidth
            bottom = top + iconDrawable.intrinsicHeight
        }
    }

    private val staticRect by lazy {
        Rect().apply {
            left = (size - iconDrawable.intrinsicWidth).div(2).toInt()
            top = (size - iconDrawable.intrinsicHeight).div(2).toInt()
            right = left + iconDrawable.intrinsicWidth
            bottom = top + iconDrawable.intrinsicHeight
        }
    }

    init {
        backgroundPaint.color = colorBackground

        circlePaint.color = colorBadge
        circlePaint.style = Paint.Style.FILL

        circleStrokePaint.color = colorBadgeStroke
        circleStrokePaint.style = Paint.Style.FILL

        backgroundPath.addOval(0f, 0f, size, size, Path.Direction.CW)
        backgroundPath.fillType = Path.FillType.EVEN_ODD
    }

    fun setPaintBackground(color: Int) {
        colorBackground = color
        backgroundPaint.color = color
    }

    fun isRotating() = rotate

    fun startRotate() {
        rotate = true
        shouldStopRotate = false
        invalidateSelf()
    }

    fun stopRotate(doWhenRotateFinished: (() -> Unit)? = null) {
        this.doWhenRotateFinished = doWhenRotateFinished
        shouldStopRotate = true
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        if (colorBackground != 0) {
            canvas.drawPath(backgroundPath, backgroundPaint)
        }

        if (colorBadge != 0) {
            canvas.drawCircle(size - badgeSize, badgeSize.toFloat(), badgeSize.toFloat() + badgeStrokeWidth, circleStrokePaint)
            canvas.drawCircle(size - badgeSize, badgeSize.toFloat(), badgeSize.toFloat(), circlePaint)
        }

        if (rotate) {
            iconDrawable.bounds = rotateRect
            canvas.save()
            canvas.translate(size / 2, size / 2)
            canvas.rotate(rotateAngle)
            iconDrawable.draw(canvas)
            canvas.restore()
            invalidateSelf()
            rotateAngle += rotateSpeed
            if (rotateAngle >= 360f || rotateAngle <= -360f) {
                rotateAngle = 0f
            }
            if (shouldStopRotate && rotateAngle == 0f) {
                rotate = false
                doWhenRotateFinished?.invoke()
            }
        } else {
            iconDrawable.bounds = staticRect
            iconDrawable.draw(canvas)
        }

        badgeDrawable?.let {
            it.bounds = Rect(
                size.toInt() - 3 * it.intrinsicWidth / 4,
                -it.intrinsicHeight / 4,
                size.toInt() + it.intrinsicWidth / 4,
                3 * it.intrinsicHeight / 4
            )
            it.draw(canvas)
        }
    }

    override fun setAlpha(alpha: Int) {
        circlePaint.alpha = alpha
        circleStrokePaint.alpha = alpha
        backgroundPaint.alpha = alpha
        iconDrawable.alpha = alpha
        badgeDrawable?.alpha = alpha
        super.setAlpha(alpha)
    }

    override fun getIntrinsicWidth(): Int {
        return size.toInt()
    }

    override fun getIntrinsicHeight(): Int {
        return size.toInt()
    }
}
