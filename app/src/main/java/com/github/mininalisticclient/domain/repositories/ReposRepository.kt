package com.github.mininalisticclient.domain.repositories

import com.github.mininalisticclient.domain.entities.DomainResult
import com.github.mininalisticclient.domain.entities.Repo
import kotlinx.coroutines.flow.Flow

interface ReposRepository {
    suspend fun fetchRepos(userLogin: String): DomainResult<List<Repo>>
    suspend fun cacheRepos(userLogin: String, repos: List<Repo>)
    fun getUserRepos(userLogin: String): Flow<DomainResult<List<Repo>>>
}
