package com.github.mininalisticclient.domain.repositories

import com.github.mininalisticclient.domain.entities.DomainResult
import com.github.mininalisticclient.domain.entities.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun fetchUsers(query: String, page: Int): List<User>
    suspend fun fetchUserDetail(userLogin: String): DomainResult<User>
    suspend fun cacheUser(user: User)
    fun getUser(userLogin: String): Flow<DomainResult<User>>
}
