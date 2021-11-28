package com.github.mininalisticclient.data.network.entities

import com.github.mininalisticclient.domain.entities.User
import com.google.gson.annotations.SerializedName
import java.util.*

data class GHUserResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("login") val login: String,
    @SerializedName("avatar_url") val avatarUrl: String?,
    @SerializedName("public_repos") val publicRepos: Int?,
    @SerializedName("public_gists") val publicGists: Int?,
    @SerializedName("followers") val followers: Int?,
    @SerializedName("following") val following: Int?,
//    @SerializedName("created_at") val createAt: Date?,
//    @SerializedName("updated_at") val updateAt: Date?,
) {
    fun toUser(): User {
        return User(id, login, avatarUrl, publicRepos, publicGists, followers, following)
    }
}
