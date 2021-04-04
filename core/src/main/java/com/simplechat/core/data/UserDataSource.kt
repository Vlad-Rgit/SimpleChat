package com.simplechat.core.data

import com.simplechat.core.domain.UserDomain

interface UserDataSource {
    suspend fun login(login: String, password: String): UserDomain
    suspend fun getCurrentUser(): UserDomain
    suspend fun loginCachedUser(): UserDomain
    suspend fun clearCachedUser()
    suspend fun addUser(user: UserDomain)
}