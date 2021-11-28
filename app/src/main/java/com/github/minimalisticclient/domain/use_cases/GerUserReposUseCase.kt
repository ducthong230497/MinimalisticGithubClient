package com.github.minimalisticclient.domain.use_cases

import com.github.minimalisticclient.domain.entities.DomainResult
import com.github.minimalisticclient.domain.entities.Repo
import com.github.minimalisticclient.domain.repositories.ReposRepository
import kotlinx.coroutines.flow.Flow

interface GetUserReposUseCase {
    fun execute(userLogin: String): Flow<DomainResult<List<Repo>>>
}

class GetUserReposUseCaseImpl(
    private val reposRepository: ReposRepository
): GetUserReposUseCase, SingleSourceStrategyUseCase<List<Repo>>() {
    override fun execute(userLogin: String): Flow<DomainResult<List<Repo>>> {
        return executeStrategy(
            databaseQuery = {
                reposRepository.getUserRepos(userLogin)
            }, networkCall = {
                reposRepository.fetchRepos(userLogin)
            }, saveCallResult = {
                it?.let { reposRepository.cacheRepos(userLogin, it) }
            }
        )
    }
}
