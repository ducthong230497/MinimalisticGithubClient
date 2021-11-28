package com.github.mininalisticclient.presenter.search_result.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.github.mininalisticclient.domain.entities.User
import com.github.mininalisticclient.domain.repositories.UserRepository

class PostDataSource(
    private val query: String,
    private val userRepository: UserRepository
): PagingSource<Int, User>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        try {
            val currentLoadingPageKey = params.key ?: 1
            val response = userRepository.fetchUsers(query, currentLoadingPageKey)
            val responseData = mutableListOf<User>()

            responseData.addAll(response)

            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1

            return LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = if (responseData.isEmpty()) null else currentLoadingPageKey.plus(1)
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
