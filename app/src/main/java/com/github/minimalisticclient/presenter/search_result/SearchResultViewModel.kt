package com.github.minimalisticclient.presenter.search_result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.github.minimalisticclient.domain.repositories.UserRepository
import com.github.minimalisticclient.presenter.search_result.adapter.UserPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

class SearchResultViewModel(
    private val query: String,
    private val userRepository: UserRepository
): ViewModel() {
    val listUsers = Pager(PagingConfig(pageSize = 30)) {
        UserPagingSource(query, userRepository)
    }.flow.flowOn(Dispatchers.IO).cachedIn(viewModelScope)
}
