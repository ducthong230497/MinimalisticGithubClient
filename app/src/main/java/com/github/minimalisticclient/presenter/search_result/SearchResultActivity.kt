package com.github.minimalisticclient.presenter.search_result

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.minimalisticclient.R
import com.github.minimalisticclient.presenter.search_result.adapter.FooterStateAdapter
import com.github.minimalisticclient.presenter.search_result.adapter.UserListAdapter
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

    private val adapter by lazy { UserListAdapter() }
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUpView() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setTitle(R.string.activity_search_result_title)
        }

        rvSearchResult.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvSearchResult.setHasFixedSize(true)
        rvSearchResult.adapter = adapter.withLoadStateFooter(
            footer = FooterStateAdapter { adapter.retry() }
        )
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
