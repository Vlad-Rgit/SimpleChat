package com.simplechat.core.data

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class UserRepository @Inject constructor(
    private val userDataSource: UserDataSource
) {

    suspend fun login(login: String, password: String) =
        userDataSource.login(login, password)

    suspend fun getCurrentUser() =
        userDataSource.getCurrentUser()

    suspend fun loginCachedUser()
        = userDataSource.loginCachedUser()


    suspend fun clearCachedUser()
        = userDataSource.clearCachedUser()

}
