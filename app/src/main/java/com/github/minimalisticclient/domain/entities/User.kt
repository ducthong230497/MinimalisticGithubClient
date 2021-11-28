package com.github.minimalisticclient.domain.entities

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") val id: Long,
    @SerializedName("login") val login: String,
    @SerializedName("avatar_url") val avatarUrl: String?,
    @SerializedName("public_repos") val publicRepos: Int?,
    @SerializedName("public_gists") val publicGists: Int?,
    @SerializedName("followers") val followers: Int?,
    @SerializedName("following") val following: Int?,
)
