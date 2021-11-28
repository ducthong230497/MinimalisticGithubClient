package com.github.minimalisticclient.data.repositories_impl

import androidx.room.withTransaction
import com.github.minimalisticclient.data.database.AppDatabase
import com.github.minimalisticclient.data.database.RepoEntity
import com.github.minimalisticclient.data.database.daos.RepoDao
import com.github.minimalisticclient.data.network.GithubApis
import com.github.minimalisticclient.domain.entities.DomainResult
import com.github.minimalisticclient.domain.entities.Repo
import com.github.minimalisticclient.domain.repositories.ReposRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class ReposRepositoryImpl(
    private val apis: GithubApis,
    private val repoDao: RepoDao,
    private val database: AppDatabase
): ReposRepository {
    override suspend fun fetchRepos(userLogin: String): DomainResult<List<Repo>> {
        return try {
            val response = apis.fetchUserRepos(userLogin)
            if (response.isSuccessful) {
                return DomainResult.success(response.body()?.map { it.toRepo() })
            }
            DomainResult.success(emptyList())
        } catch (e: Throwable) {
            e.printStackTrace()
            DomainResult.error(e)
        }
    }

    override suspend fun cacheRepos(userLogin: String, repos: List<Repo>) {
        try {
            database.withTransaction {
                repoDao.upsertAll(repos.map {
                    RepoEntity(it.id, userLogin, it.name, it.description, it.language)
                })
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    override fun getUserRepos(userLogin: String): Flow<DomainResult<List<Repo>>> {
        return repoDao.getUserRepos(userLogin).distinctUntilChanged().map {
            DomainResult.success(it.map {
                it.toRepo()
            })
        }
    }
}
