package com.simplechat.core.use_cases.splash_screen

import com.simplechat.core.data.UserRepository
import com.simplechat.core.domain.UserDomain
import javax.inject.Inject

class LoginCachedUserImpl @Inject constructor(
    private val userRepository: UserRepository
) : LoginCachedUser {
    override suspend fun invoke(): UserDomain {
        return userRepository.loginCachedUser()
    }
}