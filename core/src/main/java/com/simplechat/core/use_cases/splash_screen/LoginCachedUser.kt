package com.simplechat.core.use_cases.splash_screen


import com.simplechat.core.domain.UserDomain



interface LoginCachedUser {
    suspend operator fun invoke(): UserDomain
}