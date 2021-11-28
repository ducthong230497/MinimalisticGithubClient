package com.github.minimalisticclient.data.database

import androidx.room.*
import com.github.minimalisticclient.domain.entities.Repo

@Entity(
    tableName = "Repo", foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = arrayOf("login"),
            childColumns = arrayOf("userLogin"),
            onDelete = ForeignKey.CASCADE
        )
    ], indices = [Index("id", unique = true), Index("userLogin")]
)
data class RepoEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "userLogin") val userLogin: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "language") val language: String?,
) {
    fun toRepo(): Repo {
        return Repo(id, name, description, language)
    }
}
