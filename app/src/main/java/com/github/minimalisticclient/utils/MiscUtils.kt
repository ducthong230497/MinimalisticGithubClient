package com.github.minimalisticclient.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

class MiscUtils {
    companion object {

        fun dpToPx(context: Context, dp: Float): Float {
            return dp * context.resources.displayMetrics.density
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
