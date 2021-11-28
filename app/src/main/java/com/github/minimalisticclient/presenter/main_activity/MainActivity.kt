package com.github.minimalisticclient.presenter.main_activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.minimalisticclient.R
import com.github.minimalisticclient.presenter.search_result.SearchResultActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpViews()
    }

    private fun setUpViews() {
        userSearchBar.setOnSubmitSearch {
            search()
        }
        btnSearch.setOnClickListener {
            search()
        }
    }

    private fun search() {
        if (userSearchBar.searchString.isNotEmpty()) {
            SearchResultActivity.startActivity(this, userSearchBar.searchString)
        } else {
            Toast.makeText(this, getString(R.string.search_empty_warning), Toast.LENGTH_LONG).show()
        }
    }
}
