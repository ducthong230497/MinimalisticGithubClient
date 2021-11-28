package com.github.minimalisticclient.data.network.entities

import com.github.minimalisticclient.domain.entities.ListUser
import com.google.gson.annotations.SerializedName

data class GHListUserResponse(
    @SerializedName("total_count") val totalCount: Int?,
    @SerializedName("incomplete_results") val incompleteResults: Boolean?,
    @SerializedName("items") val items: List<GHUserResponse>,
) {
    fun toListUser(): ListUser {
        return ListUser(totalCount, incompleteResults, items)
    }
}
