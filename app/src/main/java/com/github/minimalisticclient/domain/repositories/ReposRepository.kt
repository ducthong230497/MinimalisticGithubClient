package com.github.minimalisticclient.domain.repositories

import com.github.minimalisticclient.domain.entities.DomainResult
import com.github.minimalisticclient.domain.entities.Repo
import kotlinx.coroutines.flow.Flow

interface ReposRepository {
    suspend fun fetchRepos(userLogin: String): DomainResult<List<Repo>>
    suspend fun cacheRepos(userLogin: String, repos: List<Repo>)
    fun getUserRepos(userLogin: String): Flow<DomainResult<List<Repo>>>
}
