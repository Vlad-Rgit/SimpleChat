package com.simplechat.android.framework.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.simplechat.android.framework.database.entities.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("Select * from users")
    fun getAllFlow(): Flow<List<UserEntity>>

    @Query("Select * from users")
    suspend fun getAll(): List<UserEntity>

    @Insert
    suspend fun insert(user: UserEntity): Long

    @Query("Select count(*) from users")
    suspend fun count(): Int

    @Query("Select * from users where login = :login and password = :password")
    suspend fun findByLoginPassword(
        login: String,
        password: String
    ): UserEntity?


}