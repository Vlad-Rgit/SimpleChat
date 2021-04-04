package com.simplechat.android.framework.data

import com.simplechat.android.framework.database.SimpleChatDatabase
import com.simplechat.android.framework.database.entities.UserEntity
import com.simplechat.android.framework.utils.SharedPrefsHelper
import com.simplechat.core.data.UserDataSource
import com.simplechat.core.domain.EntityMapper
import com.simplechat.core.domain.UserDomain
import com.simplechat.core.use_cases.NoUserLoggedException
import com.simplechat.core.use_cases.login_screen.ErrorCredentialsException
import com.simplechat.core.use_cases.splash_screen.NoUserCachedException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalUserDataSource @Inject constructor(
    private val database: SimpleChatDatabase,
    private val prefs: SharedPrefsHelper,
    private val userMapper: EntityMapper<UserEntity, UserDomain>
): UserDataSource {

    private var currentUser: UserDomain? = null

    override suspend fun login(login: String, password: String): UserDomain {

        val user = database.userDao
            .findByLoginPassword(login, password)
                ?: throw ErrorCredentialsException()

        val domain = userMapper.toDomain(user)

        currentUser = domain

        prefs.writeString(
            SharedPrefsHelper.userLoginKey,
            domain.login
        )

        prefs.writeString(
            SharedPrefsHelper.userPasswordKey,
            domain.password
        )

        return domain
    }

    override suspend fun getCurrentUser(): UserDomain {
        return currentUser ?: throw NoUserLoggedException()
    }

    override suspend fun loginCachedUser(): UserDomain {
        if(prefs.contains(SharedPrefsHelper.userLoginKey) &&
                prefs.contains(SharedPrefsHelper.userPasswordKey)) {
            return login(
                prefs.readString(SharedPrefsHelper.userLoginKey)!!,
                prefs.readString(SharedPrefsHelper.userPasswordKey)!!
            )
        }
        else {
            throw NoUserCachedException()
        }
    }


    override suspend fun clearCachedUser() {
        prefs.remove(SharedPrefsHelper.userLoginKey)
        prefs.remove(SharedPrefsHelper.userPasswordKey)
    }

    override suspend fun addUser(user: UserDomain) {
        val entity = userMapper.toEntity(user)
        database.userDao.insert(entity)
    }

}