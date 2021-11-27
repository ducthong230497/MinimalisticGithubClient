package com.github.mininalisticclient.presenter.main_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.github.mininalisticclient.R
import com.github.mininalisticclient.data.network.GithubApis
import com.github.mininalisticclient.presenter.search_result.SearchResultActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpViews()
    }

    private fun setUpViews() {
        userSearchBar.setOnSubmitSearch {
            if (userSearchBar.searchString.isNotEmpty()) {
                SearchResultActivity.startActivity(this, userSearchBar.searchString)
            } else {
                Toast.makeText(this, getString(R.string.search_empty_warning), Toast.LENGTH_LONG).show()
            }
        }
    }
}
