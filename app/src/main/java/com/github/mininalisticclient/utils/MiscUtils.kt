package com.github.mininalisticclient.utils

import android.content.Context
import android.content.res.Configuration
import android.graphics.Point
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager

class MiscUtils {
    companion object {
        private val point = Point()
        private val size = Size(0, 0)
        fun pxToDp(context: Context, px: Float): Float {
            return px / context.resources.displayMetrics.density
        }

        fun dpToPx(context: Context, dp: Float): Float {
            return dp * context.resources.displayMetrics.density
        }

        fun isTablet(context: Context) = context.resources.configuration.smallestScreenWidthDp >= 600

        fun isPortrait(context: Context) = context.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

        fun getScreenSize(context: Context?): Size {
            (context?.getSystemService(Context.WINDOW_SERVICE) as? WindowManager)?.run {
                defaultDisplay.getRealSize(point)
            }
            size.width = point.x
            size.height = point.y
            return size
        }

        fun getActionBarHeight(context: Context?): Int {
            var actionBarSize = 0
            val typedValue = android.util.TypedValue()
            if (context?.theme?.resolveAttribute(android.R.attr.actionBarSize, typedValue, true) == true) {
                actionBarSize = android.util.TypedValue.complexToDimensionPixelSize(typedValue.data, context.resources.displayMetrics)
            }
            return actionBarSize
        }

        fun showKeyboard(context: Context) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        }

        fun hideKeyboard(context: Context, focusView: View) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(focusView.windowToken, 0)
        }
    }
}

class Size(var width: Int, var height: Int)
