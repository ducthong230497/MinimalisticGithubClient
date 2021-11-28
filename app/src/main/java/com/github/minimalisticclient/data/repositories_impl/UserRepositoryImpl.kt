package com.github.minimalisticclient.data.repositories_impl

import androidx.room.withTransaction
import com.github.minimalisticclient.data.database.AppDatabase
import com.github.minimalisticclient.data.database.UserEntity
import com.github.minimalisticclient.data.database.daos.UserDao
import com.github.minimalisticclient.data.network.GithubApis
import com.github.minimalisticclient.domain.entities.DomainResult
import com.github.minimalisticclient.domain.entities.ListUser
import com.github.minimalisticclient.domain.entities.User
import com.github.minimalisticclient.domain.repositories.UserRepository
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class UserRepositoryImpl(
    private val apis: GithubApis,
    private val userDao: UserDao,
    private val database: AppDatabase,
    private val gson: Gson
) : UserRepository {

    override suspend fun fetchUsers(query: String, page: Int): DomainResult<ListUser> {
        return try {
            val response = apis.searchUsers(query, page)
            if (response.isSuccessful) {
                return DomainResult.success(response.body()?.toListUser())
            }
            val jsonObject = gson.fromJson(response.errorBody()?.string(), JsonObject::class.java)
            val message = jsonObject.get("message").asString
            DomainResult.error(Exception(message))
        } catch (e: Throwable) {
            e.printStackTrace()
            DomainResult.error(e)
        }
    }

    override suspend fun fetchUserDetail(userLogin: String): DomainResult<User> {
        return try {
            val response = apis.fetchUserInfo(userLogin)
            if (response.isSuccessful) {
                return DomainResult.success(response.body()?.toUser())
            }
            DomainResult.error(Exception("Failed to fetch user detail"))
        } catch (e: Throwable) {
            e.printStackTrace()
            DomainResult.error(e)
        }
    }

    override suspend fun cacheUser(user: User) {
        try {
            database.withTransaction {
                userDao.upsert(UserEntity(user.id, user.login, user.avatarUrl, user.publicRepos, user.publicGists, user.followers, user.following))
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    override fun getUser(userLogin: String): Flow<DomainResult<User>> {
        return userDao.getUser(userLogin).distinctUntilChanged().map {
            DomainResult.success(it?.toUser())
        }
    }
}
