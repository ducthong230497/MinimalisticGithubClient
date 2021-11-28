package com.github.minimalisticclient.data.network

import com.github.minimalisticclient.data.network.entities.GHListUserResponse
import com.github.minimalisticclient.data.network.entities.GHRepoResponse
import com.github.minimalisticclient.data.network.entities.GHUserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApis {
    @GET("/search/users?")
    suspend fun searchUsers(
        @Query("q") query: String,
        @Query("page") page: Int,
    ): Response<GHListUserResponse>

    @GET("/users/{userLogin}")
    suspend fun fetchUserInfo(@Path("userLogin") userLogin: String): Response<GHUserResponse>

    @GET("/users/{userLogin}/repos")
    suspend fun fetchUserRepos(@Path("userLogin") userLogin: String): Response<List<GHRepoResponse>>
}
