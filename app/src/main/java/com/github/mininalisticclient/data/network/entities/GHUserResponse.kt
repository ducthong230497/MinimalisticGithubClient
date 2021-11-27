package com.github.mininalisticclient.data.network.entities

import com.github.mininalisticclient.domain.entities.User
import com.google.gson.annotations.SerializedName

data class GHUserResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("login") val login: String,
    @SerializedName("avatar_url") val avatarUrl: String?,
) {
    fun toUser(): User {
        return User(id, login, avatarUrl)
    }
}
