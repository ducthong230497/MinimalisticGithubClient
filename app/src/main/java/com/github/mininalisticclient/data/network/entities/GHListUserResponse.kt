package com.github.mininalisticclient.data.network.entities

import com.google.gson.annotations.SerializedName

data class GHListUserResponse(
    @SerializedName("total_count") val totalCount: Int?,
    @SerializedName("incomplete_results") val incompleteResults: Boolean?,
    @SerializedName("items") val items: List<GHUserResponse>,
)
