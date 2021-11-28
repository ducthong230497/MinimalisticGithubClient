package com.github.minimalisticclient.data.database.daos

import androidx.room.Dao
import androidx.room.Query
import com.github.minimalisticclient.data.database.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class UserDao: BaseDao<UserEntity>() {

    @Query("SELECT * FROM User WHERE login = :userLogin")
    abstract fun getUser(userLogin: String): Flow<UserEntity?>
}
