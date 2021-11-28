package com.github.minimalisticclient.presenter.views

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.content.ContextCompat
import com.github.minimalisticclient.R
import com.github.minimalisticclient.utils.DrawableUtils
import com.github.minimalisticclient.utils.MiscUtils

class IconButton : AppCompatImageButton {
    private var isCircular: Boolean = false

    private var iconSource: Drawable? = null

    @ColorInt
    private var colorBackground: Int = 0

    @ColorInt
    private var colorIcon: Int = 0

    private val staticSize by lazy { MiscUtils.dpToPx(context, 30f) }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.IconButton)
        isCircular = ta.getBoolean(R.styleable.IconButton_isButtonCircular, false)
        colorBackground =
            ta.getColor(R.styleable.IconButton_buttonIconBackground, ContextCompat.getColor(context, R.color.color_fillTertiary))
        colorIcon = ta.getColor(R.styleable.IconButton_iconButtonSrcColor, ContextCompat.getColor(context, R.color.color_systemGray))
        val enabled = ta.getBoolean(R.styleable.IconButton_isButtonEnabled, true)
        iconSource = ta.getDrawable(R.styleable.IconButton_iconSource)?.mutate()
        setIconColor(colorIcon)
        isEnabled = enabled

        setIsCircular(isCircular)

        ta.recycle()
    }

    fun setIconResource(res: Int) {
        if (res == 0) {
            setImageDrawable(null)
            return
        }

        ContextCompat.getDrawable(context, res)?.let {
            setIcon(it)
        }
    }

    fun setIcon(icon: Drawable?, shouldFilterColor: Boolean = false) {
        iconSource = icon
        if (iconSource != null) {
            // TODO check for condition to apply filter color
            if (shouldFilterColor) {
                iconSource?.colorFilter = PorterDuffColorFilter(colorIcon, PorterDuff.Mode.SRC_IN)
            }
            updateIconSource()
        } else {
            setImageDrawable(null)
        }
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        isClickable = enabled
        isFocusable = enabled

        if (enabled) {
            val typedValue = TypedValue()
            context.theme.resolveAttribute(android.R.attr.selectableItemBackgroundBorderless, typedValue, true)
            setBackgroundResource(typedValue.resourceId)
        } else {
            setBackgroundResource(0)
        }

        alpha = if (!enabled) 0.4f else 1f
    }

    fun setIconSize(res: Int) {
        setIconSize(resources.getDimensionPixelSize(res).toFloat())
    }

    private fun setIconSize(size: Float) {
        drawable?.mutate()?.let {
            setImageDrawable(DrawableUtils.resize(it, resources, size))
        }
    }

    fun setIconColorResource(colorRes: Int) {
        setIconColor(ContextCompat.getColor(context, colorRes))
    }

    fun setIconColor(@ColorInt color: Int) {
        colorIcon = color
        iconSource?.colorFilter = PorterDuffColorFilter(colorIcon, PorterDuff.Mode.SRC_IN)
        invalidate()
    }

    fun setIsCircular(isCircular: Boolean) {
        this.isCircular = isCircular
        iconSource ?: return
        updateIconSource()
    }

    override fun setBackgroundColor(color: Int) {
        this.colorBackground = color
        updateIconSource()
    }

    private fun updateIconSource() {
        val currentDrawable = drawable

        if (isCircular) {
            val circleDrawable = if (currentDrawable is CircleDrawable) {
                currentDrawable.iconDrawable = iconSource!!
                currentDrawable.size = staticSize
                currentDrawable.setPaintBackground(colorBackground)
                currentDrawable.invalidateSelf()
                currentDrawable
            } else {
                CircleDrawable(iconSource!!, staticSize, colorBackground)
            }
            setImageDrawable(circleDrawable)
        } else {
            setImageDrawable(iconSource)
        }
    }
}
