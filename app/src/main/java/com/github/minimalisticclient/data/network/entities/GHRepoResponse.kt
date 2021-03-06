package com.github.minimalisticclient.data.network.entities

import com.github.minimalisticclient.domain.entities.Repo
import com.google.gson.annotations.SerializedName

data class GHRepoResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String?,
    @SerializedName("language") val language: String?,
) {
    fun toRepo(): Repo {
        return Repo(id, name, description, language)
    }
}
