package com.github.mininalisticclient.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.github.mininalisticclient.domain.entities.User

@Entity(
    tableName = "User", indices = [Index("id", unique = true), Index("login", unique = true)]
)
data class UserEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "login") val login: String,
    @ColumnInfo(name = "avatar_url") val avatarUrl: String?,
    @ColumnInfo(name = "public_repos") val publicRepos: Int?,
    @ColumnInfo(name = "public_gists") val publicGists: Int?,
    @ColumnInfo(name = "followers") val followers: Int?,
    @ColumnInfo(name = "following") val following: Int?,
) {
    fun toUser(): User {
        return User(id, login, avatarUrl, publicRepos, publicGists, followers, following)
    }
}
