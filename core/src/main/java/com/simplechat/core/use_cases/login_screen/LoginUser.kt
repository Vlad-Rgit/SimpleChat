package com.simplechat.core.use_cases.login_screen

import com.simplechat.core.domain.UserDomain

interface LoginUser {
    suspend operator fun invoke(login: String, password: String)
        : UserDomain
}