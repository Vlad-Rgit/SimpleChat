package com.simplechat.core.use_cases.login_screen

import com.simplechat.core.data.UserRepository
import com.simplechat.core.domain.UserDomain
import javax.inject.Inject

class LoginUserImpl @Inject constructor(
    private val userRepository: UserRepository
): LoginUser {

    override suspend operator fun invoke(
        login: String,
        password: String
    ): UserDomain {
        return userRepository.login(login, password)
    }
}