package com.github.mininalisticclient.data.network

import com.github.mininalisticclient.data.network.entities.GHListUserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApis {
    @GET("/search/users?")
    suspend fun searchUsers(
        @Query("q") query: String,
        @Query("page") page: Int,
    ): Response<GHListUserResponse>
}
