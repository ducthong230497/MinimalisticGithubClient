package com.github.mininalisticclient.data.repositories_impl

import com.github.mininalisticclient.data.network.GithubApis
import com.github.mininalisticclient.domain.entities.User
import com.github.mininalisticclient.domain.repositories.UserRepository

class UserRepositoryImpl(
    private val apis: GithubApis
) : UserRepository {

    override suspend fun getUsers(query: String, page: Int): List<User> {
        val response = apis.searchUsers(query, page)
        if (response.isSuccessful) {
            return response.body()?.items?.map { it.toUser() } ?: emptyList()
        }
        return emptyList()
    }
}
