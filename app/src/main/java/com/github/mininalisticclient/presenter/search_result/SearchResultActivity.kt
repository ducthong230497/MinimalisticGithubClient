package com.github.mininalisticclient.presenter.search_result

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mininalisticclient.R
import com.github.mininalisticclient.presenter.search_result.adapter.FooterStateAdapter
import com.github.mininalisticclient.presenter.search_result.adapter.UserListAdapter
import kotlinx.android.synthetic.main.activity_search_result.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SearchResultActivity: AppCompatActivity() {
    
    companion object {
        private const val QUERY = "QUERY"

        fun startActivity(activity: Activity, query: String) {
            Intent(activity, SearchResultActivity::class.java).apply {
                putExtra(QUERY, query)
                activity.startActivity(this)
            }
        }
    }

    private val adapter by lazy {
        UserListAdapter().apply {
            withLoadStateFooter(footerAdapter)
        }
    }
    private val footerAdapter by lazy { FooterStateAdapter() }
    private val query by lazy { intent.getStringExtra(QUERY) ?: "" }
    private val viewModel: SearchResultViewModel by viewModel {
        parametersOf(query)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        setUpView()
        setUpViewModels()
    }

    private fun setUpView() {
        rvSearchResult.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvSearchResult.adapter = adapter
        adapter.addLoadStateListener { loadState ->
            when  {
                loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && adapter.itemCount < 1 -> {
                    tvSearchResult?.isVisible = true
                    pbLoading?.isVisible = false
                }
                loadState.source.refresh is LoadState.Loading -> {
                    tvSearchResult?.isVisible = false
                    pbLoading?.isVisible = true
                }
                else -> {
                    tvSearchResult?.isVisible = false
                    pbLoading?.isVisible = false
                }
            }
        }
    }

    private fun setUpViewModels() {
        lifecycleScope.launch {
            viewModel.listUsers.collect {
                adapter.submitData(it)
            }
        }
    }
}
