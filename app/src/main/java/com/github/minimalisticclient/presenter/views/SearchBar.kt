package com.github.minimalisticclient.presenter.views

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.AppCompatEditText
import androidx.constraintlayout.widget.ConstraintLayout
import com.github.minimalisticclient.R
import com.github.minimalisticclient.utils.MiscUtils
import kotlinx.android.synthetic.main.view_search_bar.view.*

class SearchBar : ConstraintLayout {

    private var onSubmitSearch: (() -> Unit)? = null
    var searchString: String
        get() = etSearchBar.text.toString()
        set(value) {
            etSearchBar.setText(value)
        }

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        init(attributeSet)
    }

    constructor(context: Context, attributeSet: AttributeSet?, defStyleInt: Int) : super(
        context,
        attributeSet,
        defStyleInt
    ) {
        init(attributeSet)
    }

    private fun init(attributeSet: AttributeSet?) {
        View.inflate(context, R.layout.view_search_bar, this)

        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.SearchBar)
        val hint = typedArray.getString(R.styleable.SearchBar_searchBarHint)
        etSearchBar.hint = hint
        typedArray.recycle()

        icClearText.setOnClickListener {
            etSearchBar.text?.clear()
        }

        etSearchBar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s?.length == 0) return
                val b = s?.get(s.length - 1) == '\n'
                if (b) {
                    val textEntered = etSearchBar.text.toString()
                    etSearchBar.setText(textEntered.replace("\n", ""))
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length == 0) {
                    icClearText.visibility = View.INVISIBLE
                    return
                } else {
                    icClearText.visibility = View.VISIBLE
                }
            }
        })

        etSearchBar.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                onSubmitSearch?.invoke()
                requestClearFocus()
                return@setOnEditorActionListener true
            }
            false
        }

        requestFocus()
        MiscUtils.showKeyboard(context)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        requestClearFocus()
    }

    fun requestClearFocus() {
        etSearchBar?.let {
            clearFocus()
            MiscUtils.hideKeyboard(context, it)
        }
    }

    fun setOnSubmitSearch(listener: () -> Unit) {
        onSubmitSearch = listener
    }
}
