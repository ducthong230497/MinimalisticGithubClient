package com.github.minimalisticclient.data.network.entities

import com.github.minimalisticclient.domain.entities.User
import com.google.gson.annotations.SerializedName

data class GHUserResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("login") val login: String,
    @SerializedName("avatar_url") val avatarUrl: String?,
    @SerializedName("public_repos") val publicRepos: Int?,
    @SerializedName("public_gists") val publicGists: Int?,
    @SerializedName("followers") val followers: Int?,
    @SerializedName("following") val following: Int?,
) {
    fun toUser(): User {
        return User(id, login, avatarUrl, publicRepos, publicGists, followers, following)
    }
}
