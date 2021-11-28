package com.github.minimalisticclient.presenter.search_result.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.github.minimalisticclient.domain.entities.DomainResult
import com.github.minimalisticclient.domain.entities.User
import com.github.minimalisticclient.domain.repositories.UserRepository

class UserPagingSource(
    private val query: String,
    private val userRepository: UserRepository
): PagingSource<Int, User>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        try {
            val currentLoadingPageKey = params.key ?: 1
            val responseResult = userRepository.fetchUsers(query, currentLoadingPageKey)

            if (responseResult.status == DomainResult.Status.ERROR) {
                return LoadResult.Error(responseResult.error ?: Exception("Failed to search users"))
            }

            val responseData = responseResult.data
            val pageSize = (responseData?.totalCount ?: 0) / params.loadSize
            val users = mutableListOf<User>()
            users.addAll(responseData?.items?.map { it.toUser() } ?: emptyList())

            return LoadResult.Page(
                data = users,
                prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1,
                nextKey = if (currentLoadingPageKey < pageSize) currentLoadingPageKey + 1 else null
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}
