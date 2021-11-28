package com.github.minimalisticclient.domain.repositories

import com.github.minimalisticclient.domain.entities.DomainResult
import com.github.minimalisticclient.domain.entities.ListUser
import com.github.minimalisticclient.domain.entities.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun fetchUsers(query: String, page: Int): DomainResult<ListUser>
    suspend fun fetchUserDetail(userLogin: String): DomainResult<User>
    suspend fun cacheUser(user: User)
    fun getUser(userLogin: String): Flow<DomainResult<User>>
}
