package com.github.mininalisticclient.utils

import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.drawable.*
import android.graphics.drawable.shapes.RoundRectShape
import androidx.annotation.ColorInt

class DrawableUtils {

    companion object {

        @JvmStatic
        fun resize(
            drawable: Drawable?,
            resources: Resources,
            size: Float
        ): Drawable? {
            if (null == drawable) return drawable

            val source: Bitmap = if (drawable is VectorDrawable) {
                val bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888)
                val canvas = Canvas(bitmap)
                drawable.setBounds(0, 0, canvas.width, canvas.height)
                drawable.draw(canvas)
                bitmap
            } else {
                (drawable as BitmapDrawable).bitmap
            }

            val width = source.width
            val height = source.height

            // calculate the scale
            val scaleWidth = size / width
            val scaleHeight = size / height

            // create a matrix for the manipulation
            val matrix = Matrix()
            matrix.postScale(scaleWidth, scaleHeight)

            // recreate the new Bitmap
            val resizedBitmap = Bitmap.createBitmap(source, 0, 0, width, height, matrix, true)

            return BitmapDrawable(resources, resizedBitmap)

        }

        private fun getPressedColorSelector(@ColorInt normalColor: Int, @ColorInt pressedColor: Int): ColorStateList {
            return ColorStateList(
                arrayOf(
                    intArrayOf(android.R.attr.state_pressed),
                    intArrayOf(android.R.attr.state_focused),
                    intArrayOf(android.R.attr.state_activated),
                    intArrayOf()
                ), intArrayOf(
                    pressedColor,
                    pressedColor,
                    pressedColor,
                    normalColor
                )
            )
        }

        fun createRippleDrawable(@ColorInt normalColor: Int, @ColorInt pressedColor: Int, outerRadii: FloatArray): RippleDrawable {
            getPressedColorSelector(normalColor, pressedColor).run {
                val content = GradientDrawable()

                content.setColor(normalColor)
                val mutatedContent = content.mutate() as GradientDrawable
                mutatedContent.cornerRadii = outerRadii
                val mask = RoundRectShape(outerRadii, null, null)
                val shapeMask = ShapeDrawable(mask)
                shapeMask.paint.color = Color.WHITE
                return RippleDrawable(this, mutatedContent, shapeMask)
            }
        }

    }
}
