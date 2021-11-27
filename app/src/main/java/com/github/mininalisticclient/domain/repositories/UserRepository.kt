package com.github.mininalisticclient.domain.repositories

import com.github.mininalisticclient.domain.entities.User

interface UserRepository {
    suspend fun getUsers(query: String, page: Int): List<User>
}
