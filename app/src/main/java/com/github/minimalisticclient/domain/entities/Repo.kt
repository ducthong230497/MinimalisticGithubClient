package com.github.minimalisticclient.domain.entities

import com.google.gson.annotations.SerializedName

data class Repo(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String?,
    @SerializedName("language") val language: String?,
)
