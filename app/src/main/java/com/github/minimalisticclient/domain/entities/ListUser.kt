package com.github.minimalisticclient.domain.entities

import com.github.minimalisticclient.data.network.entities.GHUserResponse
import com.google.gson.annotations.SerializedName

data class ListUser(
    @SerializedName("total_count") val totalCount: Int?,
    @SerializedName("incomplete_results") val incompleteResults: Boolean?,
    @SerializedName("items") val items: List<GHUserResponse>,
)
