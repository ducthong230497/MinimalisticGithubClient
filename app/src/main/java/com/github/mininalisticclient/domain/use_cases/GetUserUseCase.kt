package com.github.mininalisticclient.domain.use_cases

import com.github.mininalisticclient.domain.entities.DomainResult
import com.github.mininalisticclient.domain.entities.User
import com.github.mininalisticclient.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow

interface GetUserUseCase {
    fun execute(userLogin: String): Flow<DomainResult<User>>
}

class GetUserUseCaseImpl(
    private val userRepository: UserRepository
): GetUserUseCase, SingleSourceStrategyUseCase<User>() {
    override fun execute(userLogin: String): Flow<DomainResult<User>> {
        return executeStrategy(
            databaseQuery = {
                userRepository.getUser(userLogin)
            }, networkCall = {
                userRepository.fetchUserDetail(userLogin)
            }, saveCallResult = {
                it?.let { userRepository.cacheUser(it) }
            }
        )
    }
}
