package com.github.minimalisticclient.data.database.daos

import androidx.room.Dao
import androidx.room.Query
import com.github.minimalisticclient.data.database.RepoEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class RepoDao: BaseDao<RepoEntity>() {

    @Query("SELECT * FROM Repo WHERE userLogin = :userLogin")
    abstract fun getUserRepos(userLogin: String): Flow<List<RepoEntity>>
}
